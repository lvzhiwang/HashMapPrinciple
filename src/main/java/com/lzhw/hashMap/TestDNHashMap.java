package com.lzhw.hashMap;

import java.util.HashMap;
import java.util.Map;

public class TestDNHashMap {

	public static void main(String[] args) {
		DNMap<String, String> dnmap = new DNHashMap<String, String>();
		Long t1 = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			dnmap.put("Key" + i, "Value" + i);
		}

		for (int i = 0; i < 10000; i++) {
			System.out.println("key:" + i + " , value:" + dnmap.get("Key" + i));
		}
		Long t2 = System.currentTimeMillis();

		System.err.println(t2 - t1 + "============");

	/*	System.err.println("######################################");
		Map<String, String> map = new HashMap<String, String>();
		Long t3 = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			map.put("Key" + i, "Value" + i);
		}

		for (int i = 0; i < 1000; i++) {
			System.out.println("key:" + i + " , value:" + map.get("Key" + i));
		}

		Long t4 = System.currentTimeMillis();

		System.err.println(t4 - t3 + "============");*/

	}

}
