package com.six.dcscrawler.worker;

import java.util.HashSet;
import java.util.Set;

/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
public class AgentNames {

	
	private Set<String> agentNames=new HashSet<>();
	
	
	public static AgentNames INSTANCE=new AgentNames();
	
	private AgentNames(){
		agentNames.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
		agentNames.add("CCBot");
		agentNames.add("coccoc");
		agentNames.add("Daumoa");
		agentNames.add("Googlebot");
	}
	
	public String toString(){
		StringBuilder toString=new StringBuilder();
		for(String agentName:agentNames){
			toString.append(agentName).append(",");
		}
		toString.deleteCharAt(toString.length()-1);
		return toString.toString();
	}
}
