package com.lzhw.hashMap;

import java.util.ArrayList;
import java.util.List;

public class DNHashMap<K,V> implements DNMap<K, V> {

	private static int defalultLength =16;//默认长度
	
	//负载因子,可以理解成警戒位置 
	//16*0.75=12 ，默认情况时超过12后需要扩容asas
	//如不扩容  会造成效率低（采用链表的形式遍历）
	//扩容的目的是/Library/Java/JavaVirtualMachines/为了快速的找到数据
	private static double defaultLoader=0.75;
	
	
	private Entry<K,V> [] table =null;
	
	private int size=0;
	
	public DNHashMap(int length,double loader){
		defalultLength = length;
		defaultLoader = loader;
		
		table = new Entry[defalultLength];
	}
	
	
	public DNHashMap() {
		this(defalultLength,defaultLoader);
	}

	//扩容
	private void up2size(){
		Entry<K,V>[] newTable = new Entry[2*defalultLength];
		//新创建数组后，以前老数组里的元素需要对新数组进行再散列
		againHash(newTable);
	}

	//老数据在新数组中进行再散列计算
	private void againHash(Entry<K,V>[] newTable){
		
		List<Entry<K,V>> list = new  ArrayList<Entry<K,V>>();
		
		for(int i = 0; i < table.length; i++){
			if(table[i]==null){
				continue;
			}
			findByEntryByNext(table[i], list);
			
		}
		if(list.size()>0){
			//新数组的再散列
			size=0;
			defalultLength = defalultLength*2;
			table = newTable;
			
			for(Entry<K,V> entry:list){
				if(entry.next!=null){
					entry.next=null;//原始不为空的需要全部置为空
				}
				
				put(entry.getKey(), entry.getValue());
			}
		}
		
	}

	private void findByEntryByNext(Entry<K,V> entry ,List<Entry<K,V>> list){
		if(entry!=null && entry.next!=null){
			list.add(entry);
			findByEntryByNext(entry.next, list);//递归调用
		} else{
			list.add(entry);
			
		}
	}
	
	public V put(K k, V v) {
		//判断size是否达到了扩容的标准
		if(size>=defalultLength*defaultLoader){
			up2size();
		}
		
		//1、首先创建hash函数，根据key和hash函数算出数组下标
		int index = getIndex(k);
		Entry<K,V> entry = table[index];
		
		if(entry==null){
			//entry为null，说明table的index的位置上没有元素
			table[index]= newEntry( k, v,null);
			size++;
		}else{
			//entry不为null，位置替换同时指针指向老数据
			table[index]= newEntry( k, v,entry);
		}
		return table[index].getValue();
	}
	
	private Entry<K ,V> newEntry(K k,V v, Entry<K,V> next){
		return new Entry(k, v, next);
	}
	
	
	
	private int getIndex(K k){
		int m = defalultLength;//一般情况为小于数组长度的最大质数
		int index = k.hashCode()%m;
		
		return index>=0? index:-index;
	}

	public V get(K k) {
		
		//1、首先创建hash函数，根据key和hash函数算出数组下标
		int index = getIndex(k);
		
		if(table[index]==null){
			return null;
		}
		
		return findValueByEqualKey(k,table[index]);
	}

	//依据
	public V findValueByEqualKey(K k,Entry<K, V> entry ){
		
		if(k==entry.getKey() || k.equals(entry.getKey())){
			return entry.getValue();
			
		}else{
			if(entry.next!=null){//递归调用下一个元素
				return findValueByEqualKey(k, entry.next);
			}
			
		}
		return null;
	}
	
	public int size() {
		return size;
	}

	class Entry<K,V> implements DNMap.Entry<K, V>{

		K k;
		
		V v;
		
		Entry<K,V> next;
		
		public Entry(K k,V v,Entry<K,V> next ){
			this.k=k;
			this.v=v;
			this.next = next;
		}
		
		public K getKey() {
			return k;
		}

		public V getValue() {
			return v;
		}
		
	}
}
