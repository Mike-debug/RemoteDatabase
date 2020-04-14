package remotedb;

import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;

public class attr {
	public ArrayList<String> attribution;
	public attr() {
		this.attribution = new ArrayList<String>();
		this.attribution.add("stuid");
		this.attribution.add("name");
		this.attribution.add("department");
		this.attribution.add("sender");
		this.attribution.add("age");
		this.attribution.add("specialty");
	}
	public ArrayList<String> getattr(){
		return this.attribution;
	}
}
