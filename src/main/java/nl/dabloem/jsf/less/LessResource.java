/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dabloem.jsf.less;

import com.asual.lesscss.LessEngine;
import com.asual.lesscss.LessException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Resource;
import javax.faces.application.ResourceWrapper;

/**
 *
 * @author duncan
 */
public class LessResource extends ResourceWrapper {

    private Resource wrapped;
    private LessEngine engine;
    private Boolean clientside;
    
    public LessResource(Resource resource) {
        wrapped = resource;
    }

    public LessResource(Resource wrapped, Boolean serverside) {
        this.wrapped = wrapped;
        this.clientside = serverside;
    }

    @Override
    public Resource getWrapped() {
        return wrapped;
    }

    @Override
    public Map<String, String> getResponseHeaders() {
        Map<String, String> responseHeaders = wrapped.getResponseHeaders();
        responseHeaders.put("Content-Type", "text/css");
        return responseHeaders;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        engine = new LessEngine();
        try {
            String content = slurp(wrapped.getInputStream());
            Logger.getLogger(LessResource.class.getName()).log(Level.INFO, "less file before {0}", content);
            if (!clientside){
                String compiled = engine.compile(content);
                Logger.getLogger(LessResource.class.getName()).log(Level.INFO, "css file after {0}", compiled);
                return new ByteArrayInputStream(compiled.getBytes("UTF-8"));
            } else {
                return new ByteArrayInputStream(content.getBytes("UTF-8"));
            }
        } catch (LessException ex) {
            Logger.getLogger(LessResource.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return new ByteArrayInputStream(String.format("/* %s */", ex.getMessage()).getBytes("UTF-8"));
        }
    }

    public static String slurp (InputStream in) throws IOException {
        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }     
}
