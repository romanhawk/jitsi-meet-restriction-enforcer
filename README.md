# jitsi-meet-restriction-enforcer
Jitsi Meet test application for Managed Configuration

The code is based on Enterprise Samples - AppRestrictionEnforcer https://github.com/android/enterprise-samples/tree/master/AppRestrictionEnforcer

To test Managed Configuration of Jitsi Meet
1. Build jitsi-meet-restriction-enforcer
2. Install it on device using adb
3. Run the app and setup Work Profile
4. Install Jitsi Meet with Restrictions support (i.e. using adb install command from connected PC)
5. Run jitsi-meet-restriction-enforcer inside the Work Profile and see how server URL is applied to Jitsi Meet inside the Work Profile
