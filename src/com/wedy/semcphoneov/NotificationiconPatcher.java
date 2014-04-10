package com.wedy.semcphoneov;


import android.content.res.XModuleResources;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

public class NotificationiconPatcher implements IXposedHookZygoteInit, IXposedHookInitPackageResources {
	private static XSharedPreferences preference = null;
	private static String MODULE_PATH = null;

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
	preference = new XSharedPreferences(NotificationiconPatcher.class.getPackage().getName());
		MODULE_PATH = startupParam.modulePath;
	}

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
		if (!resparam.packageName.equals("com.android.phone"))
			return;

		
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
		boolean isDataw = preference.getBoolean("key_dataw", false);

		if(isDataw){
		
			resparam.res.setReplacement("com.android.phone", "bool", "disable_charge_popups", true);
		}

		boolean isDataicon = preference.getBoolean("key_dataicon", false);

		if(isDataicon){
		
			resparam.res.setReplacement("com.android.phone", "bool", "data_connection_except_mms_show_icon_when_disabled", false);
			resparam.res.setReplacement("com.android.phone", "bool", "data_connection_except_mms_show_icon_when_enabled", false);
		}
		boolean isDataiconon = preference.getBoolean("key_dataiconon", false);

		if(isDataiconon){
		
			resparam.res.setReplacement("com.android.phone", "bool", "enable_data_off_popup", modRes.fwd(R.bool.enable_data_off_popup));
		}
		boolean isDataiconona = preference.getBoolean("key_dataiconona", false);

		if(isDataiconona){
		
			resparam.res.setReplacement("com.android.phone", "bool", "disable_data_off_popup", true);
		}
		boolean isCallrec = preference.getBoolean("key_callrec", false);

		if(isCallrec){
		
			resparam.res.setReplacement("com.android.phone", "bool", "enable_call_recording", true);

		}
		boolean isCallend = preference.getBoolean("key_callend", false);

		if(isCallend){
		
			resparam.res.setReplacement("com.android.phone", "bool", "enable_call_ended_screen", false);

		}
		boolean is3gusef = preference.getBoolean("key_3guse", false);

		if(is3gusef){
			resparam.res.setReplacement("com.android.phone", "bool", "use_3g_only", modRes.fwd(R.bool.use_3g_only));
		}
		
		
		boolean isPrefmodee = preference.getBoolean("key_prefmode", false);
		if(isPrefmodee){
			resparam.res.setReplacement("com.android.phone", "string", "preferred_network_mode_marshal", modRes.fwd(R.string.mar1));
		}
		
		boolean isPrefmodeics = preference.getBoolean("key_prefmodeics", false);

		if(isPrefmodeics){
			resparam.res.setReplacement("com.android.phone", "string", "preferred_network_mode_marshal", modRes.fwd(R.string.mar2));

		}
		boolean isAnsma = preference.getBoolean("key_ansmach", false);

		if(isAnsma){
			resparam.res.setReplacement("com.android.phone", "bool", "config_enable_answering_machine", true);

		}
		
		

	}

}
