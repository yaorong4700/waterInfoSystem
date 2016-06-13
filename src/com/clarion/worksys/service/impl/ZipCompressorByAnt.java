package com.clarion.worksys.service.impl;


import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

public class ZipCompressorByAnt {

	private File zipFile;

	public ZipCompressorByAnt(String pathName) {
		zipFile = new File(pathName);
	}
	
	public void compress(String srcPathName) {
		File srcdir = new File(srcPathName);
		if (!srcdir.exists())
			throw new RuntimeException(srcPathName + "不存在！");
		
		Project prj = new Project();
		Zip zip = new Zip();
		//zip.setEncoding("GBK");
		//zip.setEncoding("utf-8");
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		zip.addFileset(fileSet);
		
		zip.execute();
	}
}