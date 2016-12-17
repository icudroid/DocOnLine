package net.dkahn.web.dwrs;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;

public class UploadMonitor
{      
    public UploadInfo getUploadInfo()
    {
        HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();
        if (req.getSession().getAttribute("uploadInfo") != null){
            return (UploadInfo) req.getSession().getAttribute("uploadInfo");
        }else{
            return new UploadInfo();
        }
    }
}
