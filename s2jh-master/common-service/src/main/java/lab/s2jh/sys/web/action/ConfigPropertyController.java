package lab.s2jh.sys.web.action;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.sys.entity.ConfigProperty;
import lab.s2jh.sys.service.ConfigPropertyService;
import lab.s2jh.web.action.BaseController;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

@MetaData(value = "参数属性配置")
public class ConfigPropertyController extends BaseController<ConfigProperty, String> {

    @Autowired
    private ConfigPropertyService configPropertyService;

    @Override
    protected BaseService<ConfigProperty, String> getEntityService() {
        return configPropertyService;
    }

    @Override
    protected void checkEntityAclPermission(ConfigProperty entity) {
        // Nothing to do
    }

    @Override
    @MetaData(value = "保存")
    public HttpHeaders doSave() {
        return super.doSave();
    }

    @Override
    @MetaData(value = "删除")
    public HttpHeaders doDelete() {
        return super.doDelete();
    }

    @Override
    @MetaData(value = "查询")
    public HttpHeaders findByPage() {
        return super.findByPage();
    }
}