package sailing.example.streaming;


import org.apache.solr.common.SolrInputDocument;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

public  class EventParseUtil  implements Serializable {
	
    public EventParseUtil() {
		super();
	}


	public SolrInputDocument convertData (String incomingRecord) throws NumberFormatException, ParseException {
			
		 String[] inputArray = incomingRecord.split(",");
        Locale loc = new Locale("en");

		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S", loc);

//		 EventJgclDataElement eventJgclDataElement = null;
//
//
//                eventJgclDataElement = new EventJgclDataElement(
//                         inputArray[1],  inputArray[2],  inputArray[3],  inputArray[4],  inputArray[5],  inputArray[6],  inputArray[7],  inputArray[8],  inputArray[9],  inputArray[10],  inputArray[11],  inputArray[12],  inputArray[13],  inputArray[14],  inputArray[15],  sdf.parse(inputArray[16].replace("\"","")),  inputArray[17]);


		/*if (inputArray.length == 27) {
                eventJgclDataElement = new EventJgclDataElement(
                        inputArray[0],  inputArray[1],  inputArray[2],  inputArray[3],  inputArray[4],  inputArray[5],  inputArray[6],  inputArray[7],  inputArray[8],  inputArray[9],  inputArray[10],  inputArray[11],  inputArray[12],  inputArray[13],  inputArray[14],  inputArray[15],  inputArray[16],  inputArray[17],  inputArray[18],  inputArray[19],  inputArray[20],  inputArray[21],  inputArray[22],  inputArray[23],  sdf.parse(inputArray[24]),
                      inputArray[25],  inputArray[26]);

			}*/
			
//			SolrInputDocument solrDoc =
//	                  SolrSupport.autoMapToSolrInputDoc(eventJgclDataElement.getJLBH(), eventJgclDataElement, null);


        //System.out.println(incomingRecord);
        SolrInputDocument solrDoc = new SolrInputDocument();

        solrDoc.addField("ID",       inputArray[1].replace("\"",""));
        solrDoc.addField("id",       inputArray[2].replace("\"",""));
        solrDoc.addField("HPHM",     inputArray[3].replace("\"",""));
        solrDoc.addField("HPZL",     inputArray[4].replace("\"",""));
        solrDoc.addField("HPZL_MC",  inputArray[5].replace("\"",""));
        solrDoc.addField("JGSJ",     inputArray[6].replace("\"",""));
        solrDoc.addField("CLSD",     inputArray[7].replace("\"",""));
        solrDoc.addField("HPYS",     inputArray[8].replace("\"",""));
        solrDoc.addField("HPYS_MC",  inputArray[9].replace("\"",""));
        solrDoc.addField("CLLX",     inputArray[10].replace("\"",""));
        solrDoc.addField("HDSJ",     inputArray[11].replace("\"",""));
        solrDoc.addField("HDSJ_MC",  inputArray[12].replace("\"",""));
        solrDoc.addField("BZWZDM",   inputArray[13].replace("\"",""));
        solrDoc.addField("WZMC",     inputArray[14].replace("\"",""));
        solrDoc.addField("SBBH",     inputArray[15].replace("\"",""));
        solrDoc.addField("SBMC",     inputArray[16].replace("\"",""));
        solrDoc.addField("JD",       inputArray[17].replace("\"",""));
        solrDoc.addField("WD",       inputArray[18].replace("\"",""));
        solrDoc.addField("CDBH",     inputArray[19].replace("\"",""));
        solrDoc.addField("FXBH",     inputArray[20].replace("\"",""));
        solrDoc.addField("FXBH_MC",  inputArray[21].replace("\"",""));
        solrDoc.addField("CCLX",     inputArray[22].replace("\"",""));
        solrDoc.addField("BZ",       inputArray[23].replace("\"",""));
        solrDoc.addField("CSYS",     inputArray[24].replace("\"",""));
        solrDoc.addField("CSYS_MC",  inputArray[25].replace("\"",""));
        solrDoc.addField("XRSJ",     new Date());
        solrDoc.addField("XZQH",     inputArray[27].replace("\"",""));
        solrDoc.addField("XZQH_MC",  inputArray[28].replace("\"",""));
		return solrDoc;
	 }




}
