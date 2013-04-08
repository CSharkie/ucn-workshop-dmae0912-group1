package database;

import java.util.ArrayList;

import model.SalesLine;

public interface IFDBSalesLine {
	
		// get all SalesLines
			public ArrayList<SalesLine> getAllSalesLines(boolean retrieveAssociation);
		// get one SalesLine by SalesLineId;
			public SalesLine searchSalesLineId(int saleslineId, boolean retrieveAssociation);
		//insert a new SalesLine;
			public int insertSalesLine(SalesLine salesLn) throws Exception;
	}


