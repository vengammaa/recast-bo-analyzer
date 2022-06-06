package com.recast.recast.bo.analyzer.util;

import com.crystaldecisions.sdk.plugin.authentication.enterprise.IsecEnterpriseBase;
import com.crystaldecisions.sdk.plugin.authentication.ldap.IsecLDAPBase;
import com.crystaldecisions.sdk.plugin.authentication.sap.IsecSAPR3;
import com.crystaldecisions.sdk.plugin.authentication.secwinad.IsecWinADBase;

public enum AuthMode {
	ENTERPRISE (IsecEnterpriseBase.PROGID),
	LDAP (IsecLDAPBase.PROGID),
	WindowsAD (IsecWinADBase.PROGID),
	SAP (IsecSAPR3.PROGID);
	
	private String authMode;
	
	private AuthMode() {
		
	}

	private AuthMode(String authMode) {
		this.authMode = authMode;
	}

	public String getAuthMode() {
		return authMode;
	}
	
	public static AuthMode getAuthMode(String authmode){

		if ("ENTERPRISE".equalsIgnoreCase(authmode))
			return AuthMode.ENTERPRISE;
		
		if ("LDAP".equalsIgnoreCase(authmode))
			return AuthMode.LDAP;
		
		if ("WindowsAD".equalsIgnoreCase(authmode))
			return AuthMode.WindowsAD;
		
		if ("SAP".equalsIgnoreCase(authmode))
			return AuthMode.SAP;
		
		return null;
	}
}

