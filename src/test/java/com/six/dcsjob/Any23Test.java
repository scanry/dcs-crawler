package com.six.dcsjob;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.any23.Any23;
import org.apache.any23.extractor.ExtractionException;
import org.apache.any23.filter.IgnoreAccidentalRDFa;
import org.apache.any23.filter.IgnoreTitlesOfEmptyDocuments;
import org.apache.any23.writer.NTriplesWriter;
import org.apache.any23.writer.TripleHandler;
import org.apache.any23.writer.TripleHandlerException;



/**   
* @author liusong  
* @date   2017年8月17日 
* @email  359852326@qq.com 
*/
public class Any23Test {

	public static void main(String[] args) throws IOException, ExtractionException, TripleHandlerException {
		Any23 runner=new Any23();
		runner.setHTTPUserAgent("test-user-agent");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		TripleHandler handler = new NTriplesWriter(out);
		TripleHandler wrapped=new IgnoreAccidentalRDFa(new IgnoreTitlesOfEmptyDocuments(handler), true);
		
		runner.extract(new File("/Users/liusong/git/dcs-crawler/src/test/resources/test.html"), wrapped);
		wrapped.close();
		String n3 = null;
		try {
			n3 = out.toString("UTF-8");
			if (n3 != null && n3.length() < 3)
				n3 = null;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(n3);
	}

}
