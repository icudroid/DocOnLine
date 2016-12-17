package net.dkahn.web.monitored;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import net.dkahn.web.listener.OutputStreamListener;

import org.apache.commons.fileupload.disk.DiskFileItem;

public class MonitoredDiskFileItem extends DiskFileItem
{
	private static final long serialVersionUID = 1L;
	private MonitoredOutputStream mos = null;
    private OutputStreamListener listener;

    public MonitoredDiskFileItem(String fieldName, String contentType, boolean isFormField, String fileName, int sizeThreshold, File repository, OutputStreamListener listener)
    {
        super(fieldName, contentType, isFormField, fileName, sizeThreshold, repository);
        this.listener = listener;
    }

    public OutputStream getOutputStream() throws IOException
    {
        if (mos == null)
        {
            mos = new MonitoredOutputStream(super.getOutputStream(), listener);
        }
        return mos;
    }
}
