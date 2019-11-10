package com.cn.lucky.morning.model.common.tool;

import java.util.*;

public class Hashids {

    private static final String DEFAULT_ALPHABET = "xcS4F6h89aUbideAI7tkynuopqrXCgTE5GBKHLMjfRsz";
    private static final int[] PRIMES = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43 };
    private static final int[] SEPS_INDICES = { 0, 4, 8, 12 };

    private String salt_ = "";

    private String alphabet_ = "";

    private int minHashLength_;

    private ArrayList<Character> seps_ = new ArrayList<Character>();
    private ArrayList<Character> guards_ = new ArrayList<Character>();

    public Hashids() {
        this("");
    }

    public Hashids(String salt) {
        this(salt, 0);
    }

    public Hashids(String salt, int minHashLength) {
        this(salt, minHashLength, DEFAULT_ALPHABET);
    }

    public Hashids(String salt, int minHashLength, String alphabet) {
        if (alphabet == null || alphabet.trim().isEmpty()) {
            throw new IllegalArgumentException("alphabet must not be empty");
        }

        if (salt != null) {
            salt_ = salt;
        }

        if (minHashLength > 0) {
            minHashLength_ = minHashLength;
        }

        alphabet_ = join(new LinkedHashSet<String>(Arrays.asList(alphabet.split(""))), "");

        if (alphabet_.length() < 4) {
            throw new IllegalArgumentException("Alphabet must contain at least 4 unique characters.");
        }

        for (int prime : PRIMES) {
            if (prime < alphabet_.length()) {
                char c = alphabet_.charAt(prime - 1);
                seps_.add(c);
                alphabet_ = alphabet_.replace(c, ' ');
            }
        }

        for (int index : SEPS_INDICES) {
            if (index < seps_.size()) {
                guards_.add(seps_.get(index));
                seps_.remove(index);
            }
        }

        alphabet_ = consistentShuffle(alphabet_.replaceAll(" ", ""), salt_);
    }

    public String encrypt(long... numbers) {
        return encode(numbers, alphabet_, salt_, minHashLength_);
    }

    public long[] decrypt(String hash) {
        return decode(hash);
    }

    private String encode(long[] numbers, String alphabet, String salt, int minHashLength) {
        String ret = "";
        String seps = consistentShuffle(join(seps_, ""), join(numbers, ""));
        char lotteryChar = 0;

        for (int i = 0; i < numbers.length; i++) {
            if (i == 0) {
                String lotterySalt = join(numbers, "-");
                for (long number : numbers) {
                    lotterySalt += "-" + (number + 1) * 2;
                }
                String lottery = consistentShuffle(alphabet, lotterySalt);
                lotteryChar = lottery.charAt(0);
                ret += lotteryChar;

                alphabet = lotteryChar + alphabet.replaceAll(String.valueOf(lotteryChar), "");
            }

            alphabet = consistentShuffle(alphabet, ((int) lotteryChar & 12345) + salt);
            ret += hash(numbers[i], alphabet);

            if (i + 1 < numbers.length) {
                ret += seps.charAt((int) ((numbers[i] + i) % seps.length()));
            }
        }

        if (ret.length() < minHashLength) {
            int firstIndex = 0;
            for (int i = 0; i < numbers.length; i++) {
                firstIndex += (i + 1) * numbers[i];
            }

            int guardIndex = firstIndex % guards_.size();
            char guard = guards_.get(guardIndex);
            ret = guard + ret;

            if (ret.length() < minHashLength) {
                guardIndex = (guardIndex + ret.length()) % guards_.size();
                guard = guards_.get(guardIndex);
                ret += guard;
            }
        }

        while (ret.length() < minHashLength) {
            long[] padArray = new long[] { alphabet.charAt(1), alphabet.charAt(0) };
            String padLeft = encode(padArray, alphabet, salt, 0);
            String padRight = encode(padArray, alphabet, join(padArray, ""), 0);

            ret = padLeft + ret + padRight;
            int excess = ret.length() - minHashLength;
            if (excess > 0) {
                ret = ret.substring(excess / 2, excess / 2 + minHashLength);
            }
            alphabet = consistentShuffle(alphabet, salt + ret);
        }

        return ret;
    }

    private String hash(long number, String alphabet) {
        String hash = "";

        while (number > 0) {
            hash = alphabet.charAt((int) (number % alphabet.length())) + hash;
            number = number / alphabet.length();
        }

        return hash;
    }

    private long unhash(String hash, String alphabet) {
        long number = 0;

        for (int i = 0; i < hash.length(); i++) {
            int pos = alphabet.indexOf(hash.charAt(i));
            number += pos * (long) Math.pow(alphabet.length(), hash.length() - i - 1);
        }

        return number;
    }

    private long[] decode(String hash) {
        List<Long> ret = new ArrayList<Long>();
        String originalHash = hash;

        if (hash != null && !hash.isEmpty()) {
            String alphabet = "";
            char lotteryChar = 0;

            for (char guard : guards_) {
                hash = hash.replaceAll(String.valueOf(guard), " ");
            }

            String[] hashSplit = hash.split(" ");

            hash = hashSplit[hashSplit.length == 3 || hashSplit.length == 2 ? 1 : 0];

            for (char sep : seps_) {
                hash = hash.replaceAll(String.valueOf(sep), " ");
            }

            String[] hashArray = hash.split(" ");
            for (int i = 0; i < hashArray.length; i++) {
                String subHash = hashArray[i];

                if (subHash != null && !subHash.isEmpty()) {
                    if (i == 0) {
                        lotteryChar = hash.charAt(0);
                        subHash = subHash.substring(1);
                        alphabet = lotteryChar + alphabet_.replaceAll(String.valueOf(lotteryChar), "");
                    }
                }

                if (alphabet.length() > 0) {
                    alphabet = consistentShuffle(alphabet, ((int) lotteryChar & 12345) + salt_);
                    ret.add(unhash(subHash, alphabet));
                }
            }
        }

        long[] numbers = longListToPrimitiveArray(ret);

        if (!encrypt(numbers).equals(originalHash)) {
            return new long[0];
        }

        return numbers;
    }

    private static String consistentShuffle(String alphabet, String salt) {
        String ret = "";

        if (!alphabet.isEmpty()) {
            List<String> alphabetArray = charArrayToStringList(alphabet.toCharArray());
            if (salt == null || salt.isEmpty()) {
                salt = new String(new char[] { '\0' });
            }

            int[] sortingArray = new int[salt.length()];
            for (int i = 0; i < salt.length(); i++) {
                sortingArray[i] = salt.charAt(i);
            }

            for (int i = 0; i < sortingArray.length; i++) {
                boolean add = true;

                for (int k = i; k != sortingArray.length + i - 1; k++) {
                    int nextIndex = (k + 1) % sortingArray.length;

                    if (add) {
                        sortingArray[i] += sortingArray[nextIndex] + (k * i);
                    } else {
                        sortingArray[i] -= sortingArray[nextIndex];
                    }

                    add = !add;
                }

                sortingArray[i] = Math.abs(sortingArray[i]);
            }

            int i = 0;
            while (alphabetArray.size() > 0) {
                int pos = sortingArray[i];
                if (pos >= alphabetArray.size()) {
                    pos %= alphabetArray.size();
                }
                ret += alphabetArray.get(pos);
                alphabetArray.remove(pos);
                i = ++i % sortingArray.length;
            }
        }
        return ret;
    }

    public String getSalt() {
        return salt_;
    }

    public String getAlphabet() {
        return alphabet_;
    }

    public int getMinHashLength() {
        return minHashLength_;
    }

    public String getVersion() {
        return "0.1.4";
    }

    private static long[] longListToPrimitiveArray(List<Long> longs) {
        long[] longArr = new long[longs.size()];
        int i = 0;
        for (long l : longs) {
            longArr[i++] = l;
        }
        return longArr;
    }

    private static List<String> charArrayToStringList(char[] chars) {
        ArrayList<String> list = new ArrayList<String>(chars.length);
        for (char c : chars) {
            list.add(String.valueOf(c));
        }
        return list;
    }

    private static String join(long[] a, String delimiter) {
        ArrayList<String> strList = new ArrayList<String>(a.length);
        for (long l : a) {
            strList.add(String.valueOf(l));
        }
        return join(strList, delimiter);
    }

    private static String join(Collection<?> s, String delimiter) {
        Iterator<?> iter = s.iterator();
        if (iter.hasNext()) {
            StringBuilder builder = new StringBuilder(s.size());
            builder.append(iter.next());
            while (iter.hasNext()) {
                builder.append(delimiter);
                builder.append(iter.next());
            }
            return builder.toString();
        }
        return "";
    }

}
