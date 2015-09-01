package com.andaily.infrastructure;

import com.andaily.domain.shared.Application;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shengzhao Li
 */
public class STRender {

    private static String defaultSTGroupName = STRender.class.getName();

    private String filePath;
    private String encoding =  Application.ENCODING;
    private Map<String, Object> attributes = new HashMap<>();

    public STRender(String filePath) {
        Assert.notNull(filePath, "filePath is required");
        this.filePath = filePath;
    }

    public STRender(String filePath, Map<String, Object> attributes) {
        this(filePath);
        this.attributes = attributes;
    }

    //Add a new attribute
    public STRender addAttribute(String key, Object value) {
        attributes.put(key, value);
        return this;
    }

    //Default: UTF-8
    public STRender setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    /**
     * Default render by attributes
     *
     * @return Render text
     */
    public String render() {
        return render(attributes);
    }

    /**
     * Mutil time call render by different attributes
     *
     * @param attributes attributes of map
     * @return Render text
     */
    public String render(Map<String, Object> attributes) {
        final String suffix = retrieveFileSuffix();

        StringTemplateGroup group = new StringTemplateGroup(defaultSTGroupName) {

            @Override
            public InputStreamReader getInputStreamReader(InputStream in) {
                InputStreamReader isr = null;
                try {
                    isr = new InputStreamReader(in, encoding);
                } catch (UnsupportedEncodingException e) {
                    error("Invalid file character encoding: " + encoding, e);
                }
                return isr;
            }

            @Override
            public String getTemplateNameFromFileName(String fileName) {
                String name = fileName;
                int index = name.lastIndexOf(suffix);
                if (index >= 0) {
                    name = name.substring(0, index);
                }
                return name;
            }

            @Override
            public String getFileNameFromTemplateName(String templateName) {
                return templateName;
            }
        };

        StringTemplate template = group.getInstanceOf(filePath);
        template.setAttributes(attributes);

        return template.toString();
    }

    private String retrieveFileSuffix() {
        if (filePath.contains(".")) {
            int index = filePath.lastIndexOf(".");
            return filePath.substring(index, filePath.length());
        }
        return "";
    }

}