package net.dkahn.web.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class FilesForm extends ActionForm{
	private static final long serialVersionUID = 1L;
	private FormFile file1,file2,file3,file4;
	private String idFolder;
	private String description1,description2,description3,description4;
	
    public String getDescription1() {
		return description1;
	}

	public void setDescription1(String description1) {
		this.description1 = description1;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public String getDescription3() {
		return description3;
	}

	public void setDescription3(String description3) {
		this.description3 = description3;
	}

	public String getDescription4() {
		return description4;
	}

	public void setDescription4(String description4) {
		this.description4 = description4;
	}

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	public FormFile getFile1() {
        return file1;
    }

    public void setFile1(FormFile file1) {
        this.file1 = file1;
    }

    public FormFile getFile2() {
        return file2;
    }

    public void setFile2(FormFile file2) {
        this.file2 = file2;
    }

    public FormFile getFile3() {
        return file3;
    }

    public void setFile3(FormFile file3) {
        this.file3 = file3;
    }

    public FormFile getFile4() {
        return file4;
    }

    public void setFile4(FormFile file4) {
        this.file4 = file4;
    }

}
