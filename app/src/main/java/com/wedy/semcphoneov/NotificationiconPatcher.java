package com.wedy.semcphoneov;


import android.content.res.XModuleResources;
import android.view.View;

import java.util.Locale;

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
		
		if (!(resparam.packageName.equals("com.android.phone")||resparam.packageName.equals("com.android.settings")||resparam.packageName.equals("com.sonyericsson.soundenhancement")))
			return;

		if (resparam.packageName.equals("com.android.phone")){
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
		
		// Disable data charge warning
		boolean isDataw = preference.getBoolean("key_dataw", false);

		if(isDataw){
		
			resparam.res.setReplacement("com.android.phone", "bool", "disable_charge_popups", true);
		}

		// Hide icon on data state changed
		boolean isDataicon = preference.getBoolean("key_dataicon", false);

		if(isDataicon){
		
			resparam.res.setReplacement("com.android.phone", "bool", "data_connection_except_mms_show_icon_when_disabled", false);
			resparam.res.setReplacement("com.android.phone", "bool", "data_connection_except_mms_show_icon_when_enabled", false);
		}
		
		// Disable data off notify
		boolean isDataiconon = preference.getBoolean("key_dataiconon", false);

		if(isDataiconon){
		
			resparam.res.setReplacement("com.android.phone", "bool", "enable_data_off_popup", modRes.fwd(R.bool.enable_data_off_popup));
		}
		
		// Disable data off notify
		boolean isDataiconona = preference.getBoolean("key_dataiconona", false);

		if(isDataiconona){
		
			resparam.res.setReplacement("com.android.phone", "bool", "disable_data_off_popup", true);
		}
		
		// Enable call recording
		boolean isCallrec = preference.getBoolean("key_callrec", false);

		if(isCallrec){
		
			resparam.res.setReplacement("com.android.phone", "bool", "enable_call_recording", true);

		}
		
		// Disable call ended screen (SemcPhone)
		boolean isCallend = preference.getBoolean("key_callend", false);

		if(isCallend){
		
			resparam.res.setReplacement("com.android.phone", "bool", "enable_call_ended_screen", false);

		}
		
		// Show Use only 3G networks
		boolean is3gusef = preference.getBoolean("key_3guse", false);

		if(is3gusef){
			resparam.res.setReplacement("com.android.phone", "bool", "use_3g_only", modRes.fwd(R.bool.use_3g_only));
		}
		
		// Change network mode
		boolean isPrefmodee = preference.getBoolean("key_prefmode", false);
		if(isPrefmodee){
			resparam.res.setReplacement("com.android.phone", "string", "preferred_network_mode_marshal", s);
		}
		
		// Enable Answering Machine
		boolean isAnsma = preference.getBoolean("key_ansmach", false);

		if(isAnsma){
			resparam.res.setReplacement("com.android.phone", "bool", "config_enable_answering_machine", true);

		}
		
		// Show VoLTE option
		boolean isVolte = preference.getBoolean("key_volte", false);
		
		if(isVolte){
			resparam.res.setReplacement("com.android.phone", "bool", "config_enable_volte_toggle_setting", true);
			resparam.res.setReplacement("com.android.phone", "bool", "config_enable_volte_default_value", true);
			resparam.res.setReplacement("com.android.phone", "bool", "config_volte_control_modem_policy", true);
			resparam.res.setReplacement("com.android.phone", "bool", "config_volte_modem_policy_follow_mobile_data_setting", true);
		}
		
		// Show turn off camera option
		boolean isCamoff = preference.getBoolean("key_camoff", false);

		if(isCamoff){
			resparam.res.setReplacement("com.android.phone", "bool", "enable_camera_off_button_during_video_call", true);

		}
		
		// Show voice only answer option
		boolean isVoonly = preference.getBoolean("key_voonly", false);

		if(isVoonly){
			resparam.res.setReplacement("com.android.phone", "bool", "enable_voice_only_answer_during_video_incoming_call", true);

		}
		
		// Hide video call button
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
		
		// Hide reject call with message
		boolean isRejectmsg = preference.getBoolean("key_rejectmsg", false);

		if(isRejectmsg){
			resparam.res.setReplacement("com.android.phone", "dimen", "somc_incallscreen_reject_msgs_drawer_height", modRes.fwd(R.dimen.somc_incallscreen_reject_msgs_drawer_height));
			resparam.res.setReplacement("com.android.phone", "dimen", "incall_screen_call_large_Header_height", modRes.fwd(R.dimen.incall_screen_call_large_Header_height));
			resparam.res.hookLayout("com.android.phone", "layout", "somc_incallscreen_reject_msgs_list_item", new XC_LayoutInflated() {
			    @Override
			    public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
			    	liparam.view.findViewById(liparam.res.getIdentifier("text", "id", "com.android.phone")).setVisibility(View.GONE);
			    }
			    }); 
		}
		
		// Hide reject call with message for E1
		boolean isRejectmsge1 = preference.getBoolean("key_rejectmsge1", false);

		if(isRejectmsge1){
			resparam.res.setReplacement("com.android.phone", "bool", "reject_msg_list_disabled_when_call_waiting", true);
		}
		
		}//End
		
		
		// Z3 InCallUI.apk
		if (resparam.packageName.equals("com.android.incallui")){
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
		
		// Disable call ended screen
		boolean isCallend3 = preference.getBoolean("key_callend3", false);

		if(isCallend3){
		
			resparam.res.setReplacement("com.android.incallui", "bool", "enable_call_ended_screen", false);

		}
		
		// Hide reject call with message
		boolean isRejectmsgz3 = preference.getBoolean("key_rejectmsgz3", false);

		if(isRejectmsgz3){
			resparam.res.setReplacement("com.android.incallui", "dimen", "reject_msgs_drawer_height", modRes.fwd(R.dimen.reject_msgs_drawer_height));
			resparam.res.setReplacement("com.android.incallui", "dimen", "call_large_header_height", modRes.fwd(R.dimen.call_large_header_height));
			/*resparam.res.hookLayout("com.android.incallui", "layout", "somc_reject_with_msgs_list_item", new XC_LayoutInflated() {
			    @Override
			    public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
			    	liparam.view.findViewById(liparam.res.getIdentifier("text", "id", "com.android.incallui")).setVisibility(View.GONE);
			    }
			    }); */
		}

		}//End
		
		
		// Z3 SoundEnhancement.apk
		if (resparam.packageName.equals("com.sonyericsson.soundenhancement")){
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
		
		// Disable call ended screen
		boolean isSoundeh = preference.getBoolean("key_soundeh", false);

		if(isSoundeh){
		
			resparam.res.setReplacement("com.sonyericsson.soundenhancement", "bool", "enabled_xloud", true);
			resparam.res.setReplacement("com.sonyericsson.soundenhancement", "bool", "enabled_clear_stereo", true);
			resparam.res.setReplacement("com.sonyericsson.soundenhancement", "bool", "enabled_clear_phase", true);

		}
		

		}//End

	}

}
