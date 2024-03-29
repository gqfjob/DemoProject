package lab.s2jh.rpt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseUuidEntity;
import lab.s2jh.core.entity.annotation.EntityAutoCode;
import lab.s2jh.sys.entity.AttachmentFile;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

@Entity
@Table(name = "tbl_RPT_REPORT_DEF")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "报表定义")
public class ReportDef extends BaseUuidEntity {

    @MetaData(value = "代码")
    @EntityAutoCode(order = 5, search = true)
    private String code;

    @MetaData(value = "名称", tooltips = "此名称会作为报表下载文件名称")
    @EntityAutoCode(order = 10, search = true)
    private String title;

    @MetaData(value = "描述", tooltips = " 对于报表用法的描述")
    @EntityAutoCode(order = 20, search = false, listShow = false)
    private String description;

    @MetaData(value = "类型")
    @EntityAutoCode(order = 30, search = true)
    private ReportTypeEnum type;

    @MetaData(value = "分类", tooltips = "对于报表的分类，方便后续按类别显示")
    @EntityAutoCode(order = 40, search = true)
    private String category;

    @MetaData(value = "排序号", tooltips = "用于在列表显示确定先后顺序")
    @EntityAutoCode(order = 60, search = false)
    private Integer orderRank = 100;

    @MetaData(value = "禁用标识", tooltips = "禁用全局不显示")
    @EntityAutoCode(order = 70, search = true)
    private Boolean disabled = Boolean.FALSE;

    @MetaData(value = "模板文件ID")
    @EntityAutoCode(order = 100, search = false)
    private AttachmentFile templateFile;

    @MetaData(value = "关联的报表参数", tooltips = "一般主要用于JasperReport类型报表,JXLS类型一般是在每个业务Action方法中特定组织参数对象给JXLS解析处理")
    private List<ReportParam> reportParameters;

    @MetaData(value = "角色关联")
    private List<ReportDefR2Role> reportDefR2Roles = Lists.newArrayList();
    
    public static enum ReportTypeEnum {

        /** 
         * 基于JXLS定制化Excel输出 
         * 在Excel模板中嵌入JXLS脚本代码，然后在各业务Action方法中组织想的参数传入JXLS上下文
         * 然后由JXLS负责转换生成输出的Excel文件
         */
        @MetaData(value = "Excel(JXLS)")
        EXCEL_JAVA,

        /** 
         * 基于VBS的Excel模板，一般是在Excel中直接嵌入报表处理逻辑，提供用户下载此Excel后运行Excel即可生成报表
         * 此模式基本是独立于应用实现的报表处理方式，完全由线下开发VBS脚本Excel文件即可 
         */
        @MetaData(value = "Excel(VBS)")
        EXCEL_VBS,

        /** 
         * 基于iReport设计的JasperReport模板文件
         * 一般提供有iReport编译完成的.jasper文件作为模板文件提供给JasperReport API解析处理
         */
        @MetaData(value = "JasperReport")
        JASPER;
    }

    @Override
    @Transient
    public String getDisplay() {
        return title;
    }

    @Column(nullable = false, unique = true, length = 64)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(length = 128, nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(length = 1024, nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    @Column(length = 32, nullable = false)
    public ReportTypeEnum getType() {
        return type;
    }

    public void setType(ReportTypeEnum type) {
        this.type = type;
    }

    @Column(length = 128, nullable = false)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getOrderRank() {
        return orderRank;
    }

    public void setOrderRank(Integer orderRank) {
        this.orderRank = orderRank;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @OneToMany(mappedBy = "reportDef", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy(value = "orderRank desc")
    public List<ReportParam> getReportParameters() {
        return reportParameters;
    }

    public void setReportParameters(List<ReportParam> reportParameters) {
        this.reportParameters = reportParameters;
    }

    @OneToMany(mappedBy = "reportDef", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    public List<ReportDefR2Role> getReportDefR2Roles() {
        return reportDefR2Roles;
    }

    public void setReportDefR2Roles(List<ReportDefR2Role> reportDefR2Roles) {
        this.reportDefR2Roles = reportDefR2Roles;
    }

    @OneToOne
    @JoinColumn(name = "FILE_ID")
    public AttachmentFile getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(AttachmentFile templateFile) {
        this.templateFile = templateFile;
    }

    @Transient
    @JsonIgnore
    public String getReportAccessUrl() {
        return "/rpt/jasper-report!show?report=" + code;
    }
}
