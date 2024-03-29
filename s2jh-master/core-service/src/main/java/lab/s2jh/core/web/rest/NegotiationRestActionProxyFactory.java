package lab.s2jh.core.web.rest;

import java.util.Map;

import org.apache.struts2.rest.RestActionInvocation;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.DefaultActionProxyFactory;
import com.opensymphony.xwork2.inject.Inject;

public class NegotiationRestActionProxyFactory extends DefaultActionProxyFactory {

    public static final String STRUTS_REST_NAMESPACE = "struts.rest.namespace";

    protected String namespace;

    @Inject(value = STRUTS_REST_NAMESPACE, required = false)
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    private final static NegotiationContentTypeHandlerManager contentTypeHandlerManager = new NegotiationContentTypeHandlerManager();

    @Override
    public ActionProxy createActionProxy(String namespace, String actionName, String methodName,
            Map<String, Object> extraContext, boolean executeResult, boolean cleanupContext) {
        if (this.namespace == null || namespace.startsWith(this.namespace)) {
            RestActionInvocation inv = new NegotiationRestActionInvocation(extraContext, true);
            container.inject(inv);
            container.inject(contentTypeHandlerManager);
            inv.setMimeTypeHandlerSelector(contentTypeHandlerManager);
            return createActionProxy(inv, namespace, actionName, methodName, executeResult, cleanupContext);
        } else {
            return super.createActionProxy(namespace, actionName, methodName, extraContext, executeResult,
                    cleanupContext);
        }
    }

}
