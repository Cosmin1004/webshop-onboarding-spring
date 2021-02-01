package com.youngculture.webshoponboardingspring.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class FormatTextTag extends SimpleTagSupport {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        String formattedText = text.toLowerCase().substring(0, 1).toUpperCase()
                + text.toLowerCase().substring(1);
        out.print(formattedText);
    }


}
