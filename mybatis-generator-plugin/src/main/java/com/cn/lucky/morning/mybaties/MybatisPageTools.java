package com.cn.lucky.morning.mybaties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

public class MybatisPageTools extends PluginAdapter {
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        // add field, getter, setter for limit clause
        addLimit(topLevelClass, introspectedTable, "limitStart");
        addLimit(topLevelClass, introspectedTable, "limitEnd");
        addPage(topLevelClass, introspectedTable);
//        addPageTemplate(topLevelClass, introspectedTable);
        return super.modelExampleClassGenerated(topLevelClass,introspectedTable);
    }
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement isParameterPresenteElemen = element;//(XmlElement) element.getElements().get(element.getElements().size());
        XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
        isNotNullElement.addAttribute(new Attribute("test", "limitStart > -1")); //$NON-NLS-1$ //$NON-NLS-2$
        isNotNullElement.addElement(new TextElement("limit ${limitStart} , ${limitEnd}"));
        isParameterPresenteElemen.addElement(isNotNullElement);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }
    private void addLimit(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(FullyQualifiedJavaType.getIntInstance());
        field.setName(name);
        field.setInitializationString("-1");
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), name));
        method.addBodyLine("this." + name + "=" + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    private void addPage(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("setPage");
        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "page"));
        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "size"));
        method.addBodyLine("this.limitStart = page * size;this.limitEnd = size;");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }


    private void addPageTemplate(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("pageByExample");

        FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        FullyQualifiedJavaType baseQueryType = new FullyQualifiedJavaType("com.diligrp.shop.constant.BaseQuery");
        FullyQualifiedJavaType pageTmplType = new FullyQualifiedJavaType("com.diligrp.shop.constant.PageTemplate");

        String recordType = introspectedTable.getBaseRecordType();

        method.setReturnType(pageTmplType);

        method.addParameter(new Parameter(exampleType, "example"));
        method.addParameter(new Parameter(baseQueryType, "baseQuery"));

        StringBuffer code = new StringBuffer(32);
        code.append("int count = this.countByExample(example);\n");
        code.append("List<" + recordType + "> list = this.selectByExample(example);\n");
        code.append("com.diligrp.shop.constant.PageTemplate<" + recordType + "> pageTemplate = com.diligrp.shop.constant.PageTemplate.build();\n ");
        code.append("pageTemplate.setTotalSize(count);\n");
        code.append("pageTemplate.setList(list);\n");
        code.append("if(baseQuery != null){");
        code.append("pageTemplate.setCurrPage(baseQuery.getCurrent());\n");
        code.append("pageTemplate.setPageSize(baseQuery.getSize());\n");
        code.append("}");
        code.append("return pageTemplate;");

        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    /**
     * This plugin is always valid - no properties are required
     */
    public boolean validate(List<String> warnings) {
        return true;
    }
    public static void generate() {
        String config = "/media/plant/_dde_data/customer/codes/java/DouBiNovel/mybatis-generator-plugin/src/main/resources/generatorConfig.xml";
        String[] arg = { "-configfile", config, "-overwrite" };
        ShellRunner.main(arg);
    }
    public static void main(String[] args) {
        generate();
    }

}
