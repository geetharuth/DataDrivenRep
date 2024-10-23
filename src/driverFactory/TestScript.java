package driverFactory;

import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import applicationLayer.Com.Page.CustomerPage;
import config.AppUtil1;
import utilities.ExcelFileUtil;

public class TestScript extends AppUtil1{
String inputpath="./FileInput/Customerdata.xlsx";
String outputpath="./FileOutput/Customerresults.xlsx";
String Tcsheet="Customer";
ExtentReports reports;
ExtentTest Logger;
@Test
public void starttest() throws Throwable
{
	reports=new ExtentReports("./Reports/customer.html");
	//create for object for excelfileutil class
	ExcelFileUtil xl=new ExcelFileUtil(inputpath);
	//count no of rows in tcsheet
	int rc =xl.rowcount(Tcsheet);
Reporter.log("no of rows are::"+rc,true);
for(int i=1;i<=rc;i++)
{
	Logger = reports.startTest("customer");
	Logger.assignAuthor("anjali");
	//read cell from sheet
	String cname=xl.getceldata(Tcsheet, i, 0);
	String address=xl.getceldata(Tcsheet, i, 1);
	String city=xl.getceldata(Tcsheet, i, 2);
	String country=xl.getceldata(Tcsheet, i, 3);
	String cperson=xl.getceldata(Tcsheet, i, 4);
	String pnumber=xl.getceldata(Tcsheet, i, 5);
	String email=xl.getceldata(Tcsheet, i, 6);
	String mnumber=xl.getceldata(Tcsheet, i, 7);
	String notes=xl.getceldata(Tcsheet, i, 8);
	Logger.log(LogStatus.INFO,cname+"      "+address+"   "+city+"     "+country+"     "+cperson+"   "+pnumber+"    "+email+"      "+mnumber+" "+notes);
	CustomerPage customeradd =PageFactory.initElements(driver, CustomerPage.class);
	boolean res=customeradd.addCustomer(cname, address, city, country, cperson, pnumber, email, mnumber, notes);
	if(res)
	{
		xl.setcellData(Tcsheet, i, 9, "pass",outputpath );
		Logger.log(LogStatus.PASS, "customer add sucess");
	}
	else
	{
		xl.setcellData(Tcsheet, i, 9, "fail",outputpath );
		Logger.log(LogStatus.FAIL, "customer add sucess");
	}
	reports.endTest(Logger);
	reports.flush();
}
	
}

}
