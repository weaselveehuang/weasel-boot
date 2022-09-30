package com.weasel.modules.sys.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * @author weasel
 * @date 2022/5/25 18:25
 * @version 1.0
 */
@RestController
@RequestMapping("/sys/dataSource")
class A {

    @GetMapping(value = "a.xml", produces = "application/xml; charset=UTF-8")
    @ResponseBody
    def a() {
        '''<?xml version="1.0" encoding="UTF-8"?>
<catalog name="THREDDS Server Default Catalog : You must change this to fit your server!"
         xmlns="http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0"
         xmlns:xlink="http://www.w3.org/1999/xlink"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0
           https://schemas.unidata.ucar.edu/thredds/InvCatalog.1.0.6.xsd">
  
  <service name="all1" base="" serviceType="compound">
    <service name="odap1" serviceType="OpenDAP" base="http://localhost:8080/thredds/dodsC/"/>
    <service name="dap41" serviceType="DAP4" base="http://localhost:8080/thredds/dap4/"/>
    <service name="http1" serviceType="HTTPServer" base="http://localhost:8080/thredds/fileServer/"/>
    <service name="wcs1" serviceType="WCS" base="http://localhost:8080/thredds/wcs/"/>
    <service name="wms1" serviceType="WMS" base="http://localhost:8080/thredds/wms/"/>
    <service name="ncssGrid1" serviceType="NetcdfSubset" base="http://localhost:8080/thredds/ncss/grid/"/>
    <service name="ncssPoint1" serviceType="NetcdfSubset" base="http://localhost:8080/thredds/ncss/point/"/>
    <service name="cdmremote1" serviceType="CdmRemote" base="http://localhost:8080/thredds/cdmremote/"/>
    <service name="iso1" serviceType="ISO" base="http://localhost:8080/thredds/iso/"/>
    <service name="ncml1" serviceType="NCML" base="http://localhost:8080/thredds/ncml/"/>
    <service name="uddc1" serviceType="UDDC" base="http://localhost:8080/thredds/uddc/"/>
  </service>

  <datasetRoot path="test1" location="content/testdata/" />

  <dataset name="Test Grid Dataset1" ID="testGrid1"
           serviceName="all1"  urlPath="test/crossSeamProjection.nc" dataType="Grid"/>

  <dataset name="Test Point Dataset1" ID="testPoint1"
           serviceName="all1" urlPath="test/H.1.1.nc" dataType="Point"/>
  
  <dataset name="Test Station Dataset1" ID="testStation1"
           serviceName="all1" urlPath="test/H.2.1.1.nc" dataType="Point"/>

  <datasetScan name="Test all files in a directory1" ID="testDatasetScan1"
               path="testAll1" location="D:/thredds/public/testdata">
    <metadata inherited="true">
      <serviceName>all1</serviceName>
    </metadata>
  </datasetScan>
</catalog>

'''
    }

}
