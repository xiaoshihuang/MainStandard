package com.xintai.xhao.bean;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 序列化：为了保存在内存中的各种对象的状态，并可以把保存的对象的状态读出来。
 * 1.注意：对一个对象进行序列化的时候，如果它里面包含子对象，那么子对象也必须序列化。
 * 详解：http://www.jianshu.com/p/a60b609ec7e7
 * @author Administrator
 */

//		Intent传递对象需要序列化Serializable,Parcelable
//		Serializable使用简洁，这种序列化用到反射机制，期间创建了大量的临时对象，内存开销大，会引起GC频繁回收调用资源。
//		Parcelable速度快，性能更高，代码量大，加大维护成本。
//		使用：Serializable可将数据持久化方便保存，所以在需要保存或网络传输数据时选择Serializable;
// 		Activity，Service,Fragment之间传递数据使用Parcelable。
public class A2 implements Parcelable{

	private String userName;
	private String passWord;
	private String deviceId;
	private List<A2Bean> data;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public List<A2Bean> getData() {
		return data;
	}

	public void setData(List<A2Bean> data) {
		this.data = data;
	}

	//Parcelable序列化实现方法
	public A2(){
		
	}
	
	 // 系统自动添加，给createFromParcel里面用
	public A2(Parcel in) {  
		userName = in.readString();  
		passWord = in.readString();  
		deviceId = in.readString();  
		data = in.readArrayList(A2Bean.class.getClassLoader());
    }  

	// 内容接口描述，默认返回0即可。
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		// TODO Auto-generated method stub
		 dest.writeString(getUserName());  
		 dest.writeString(getPassWord());  
		 dest.writeString(getDeviceId());  
		 dest.writeList(data);
	}
	
	  public static final Creator<A2> CREATOR = new Creator<A2>() {
		  
	        public A2 createFromParcel(Parcel in) {  
	            return new A2(in);  // 在构造函数里面完成了 读取 的工作
	        }  
	        
	        //供反序列化本类数组时调用的
	        public A2[] newArray(int size) {  
	            return new A2[size];  
	        }  
	    };  

}
