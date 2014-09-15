package com.wedy.semcphoneov;


import java.util.Locale;

import android.content.res.XModuleResources;
import android.view.View;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;

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
		String s = preference.getString("key_prefedit", "0,1,2,3,4,5,6,7,8,9,10,11");
		
		if (!(resparam.packageName.equals("com.android.phone")||resparam.packageName.equals("com.android.settings")))
			return;

		if (resparam.packageName.equals("com.android.phone")){
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
			resparam.res.setReplacement("com.android.phone", "string", "preferred_network_mode_marshal", s);
		}
		
		boolean isAnsma = preference.getBoolean("key_ansmach", false);

		if(isAnsma){
			resparam.res.setReplacement("com.android.phone", "bool", "config_enable_answering_machine", true);

		}
		boolean isVolte = preference.getBoolean("key_volte", false);
		
		if (Locale.JAPAN.equals(Locale.getDefault()) && isVolte){
			resparam.res.setReplacement("com.android.phone", "bool", "config_enable_volte_toggle_setting", true);
			resparam.res.setReplacement("com.android.phone", "string", "volte_toggle_title", modRes.fwd(R.string.volte_toggle_title));
		}
		else if(isVolte){
			resparam.res.setReplacement("com.android.phone", "bool", "config_enable_volte_toggle_setting", true);
		}
		boolean isCamoff = preference.getBoolean("key_camoff", false);

		if(isCamoff){
			resparam.res.setReplacement("com.android.phone", "bool", "enable_camera_off_button_during_video_call", true);

		}
		boolean isVoonly = preference.getBoolean("key_voonly", false);

		if(isVoonly){
			resparam.res.setReplacement("com.android.phone", "bool", "enable_voice_only_answer_during_video_incoming_call", true);

		}
		boolean isNovobut = preference.getBoolean("key_novcbutton", false);

		if(isNovobut){
			resparam.res.hookLayout("com.android.phone", "layout", "somc_incallscreen_large", new XC_LayoutInflated() {
			    @Override
			    public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
			    	View clock = (View) liparam.view.findViewById(
			    	liparam.res.getIdentifier("switchToVideoButton", "id", "com.android.phone"));
			    	clock.setVisibility(View.GONE);
			    }
			    }); 
		}
		}
		if (resparam.packageName.equals("com.android.incallui")){
			boolean isCallend3 = preference.getBoolean("key_callend3", false);

		if(isCallend3){
		
			resparam.res.setReplacement("com.android.incallui", "bool", "enable_call_ended_screen", false);

		}

		}

	}

}
