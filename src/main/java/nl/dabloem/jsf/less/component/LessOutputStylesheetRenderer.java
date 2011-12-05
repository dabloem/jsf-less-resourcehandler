/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dabloem.jsf.less.component;

import com.sun.faces.renderkit.html_basic.ScriptStyleBaseRenderer;
import java.io.IOException;
import java.util.Map;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 * @author duncan
 */
public class LessOutputStylesheetRenderer extends ScriptStyleBaseRenderer {
    
    @Override
    protected void startElement(ResponseWriter writer, UIComponent component) throws IOException {
        writer.startElement("style", component);
        writer.writeAttribute("type", "text/css", "type");
    }
    
    @Override
    protected void endElement(ResponseWriter writer) throws IOException {
        writer.endElement("style");
    }

    @Override
    protected String verifyTarget(String toVerify) {
        return "head";
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
          throws IOException {

        Map<String,Object> attributes = component.getAttributes();
        Map<Object, Object> contextMap = context.getAttributes();

        String name = (String) attributes.get("name");
        String library = (String) attributes.get("library");
        String key = name + library;

        String media = (String) attributes.get("media");
        
        if (null == name) {
            return;
        }
        
        // Ensure this stylesheet is not rendered more than once per request
        if (contextMap.containsKey(key)) {
            return;
        }
        contextMap.put(key, Boolean.TRUE);
        
        Resource resource = context.getApplication().getResourceHandler()
              .createResource(name, library);

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("link", component);
        writer.writeAttribute("type", "text/css", "type");
        writer.writeAttribute("rel", "stylesheet/less", "rel");
        String resourceUrl = "RES_NOT_FOUND";
        if (resource != null) {
        	resourceUrl = context.getExternalContext().encodeResourceURL(resource.getRequestPath());
        }
        writer.writeURIAttribute("href", resourceUrl, "href");
        if (media != null) {
            writer.writeAttribute("media", media, "media");
        }
        writer.endElement("link");
        super.encodeEnd(context, component);
    }
}
