/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.lessresourcehandler;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.context.FacesContext;

/**
 *
 * @author duncan
 */
public class LessResourceHandler extends ResourceHandlerWrapper {

    private ResourceHandler wrapped;
    
    public LessResourceHandler(ResourceHandler handler) {
        wrapped = handler;
    }
      
    @Override
    public Resource createResource(String resourceName) {
        return createResource(resourceName, null);
    }

    @Override
    public Resource createResource(String resourceName, String libraryName) {
        if (resourceName.endsWith(".less")){
            FacesContext fc = FacesContext.getCurrentInstance();
            String serverside = fc.getExternalContext().getInitParameter("less.serverside");
            Logger.getLogger(LessResourceHandler.class.getName()).log(Level.INFO, "Less Resource Handler scanning {0}", resourceName);
            Resource createResource = wrapped.createResource(resourceName, libraryName);
            return new LessResource(createResource, Boolean.parseBoolean(serverside));
        }
        return wrapped.createResource(resourceName, libraryName);
    }    
    
    @Override
    public ResourceHandler getWrapped() {
        return wrapped;
    }
  
}
