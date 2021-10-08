package model;

public class BlogTemplate {
    private long templateId;
    private String templateName;
    private String fontColor;
    private Category category;

    public BlogTemplate(long templateId, String templateName, String fontColor, Category category) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.fontColor = fontColor;
        this.category = category;
    }
}
