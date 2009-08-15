/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.bluetooth;

import android.os.RemoteException;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

/**
 * Represents the local Bluetooth adapter.
 *
 * @hide
 */
public final class BluetoothAdapter {
    private static final String TAG = "BluetoothAdapter";

    public static final int BLUETOOTH_STATE_OFF = 0;
    public static final int BLUETOOTH_STATE_TURNING_ON = 1;
    public static final int BLUETOOTH_STATE_ON = 2;
    public static final int BLUETOOTH_STATE_TURNING_OFF = 3;

    /** Inquiry scan and page scan are both off.
     *  Device is neither discoverable nor connectable */
    public static final int SCAN_MODE_NONE = 0;
    /** Page scan is on, inquiry scan is off.
     *  Device is connectable, but not discoverable */
    public static final int SCAN_MODE_CONNECTABLE = 1;
    /** Page scan and inquiry scan are on.
     *  Device is connectable and discoverable */
    public static final int SCAN_MODE_CONNECTABLE_DISCOVERABLE = 3;

    public static final int RESULT_FAILURE = -1;
    public static final int RESULT_SUCCESS = 0;

    /* The user will be prompted to enter a pin */
    public static final int PAIRING_VARIANT_PIN = 0;
    /* The user will be prompted to enter a passkey */
    public static final int PAIRING_VARIANT_PASSKEY = 1;
    /* The user will be prompted to confirm the passkey displayed on the screen */
    public static final int PAIRING_VARIANT_CONFIRMATION = 2;

    private final IBluetooth mService;

    /**
     * Do not use this constructor. Use Context.getSystemService() instead.
     * @hide
     */
    public BluetoothAdapter(IBluetooth service) {
        if (service == null) {
            throw new IllegalArgumentException("service is null");
        }
        mService = service;
    }

    /**
     * Get the remote BluetoothDevice associated with the given MAC address.
     * Bluetooth MAC address must be upper case, such as "00:11:22:33:AA:BB".
     * @param address valid Bluetooth MAC address
     */
    public BluetoothDevice getRemoteDevice(String address) {
        return new BluetoothDevice(address);
    }

    /**
     * Is Bluetooth currently turned on.
     *
     * @return true if Bluetooth enabled, false otherwise.
     */
    public boolean isEnabled() {
        try {
            return mService.isEnabled();
        } catch (RemoteException e) {Log.e(TAG, "", e);}
        return false;
    }

    /**
     * Get the current state of Bluetooth.
     *
     * @return One of BLUETOOTH_STATE_ or BluetoothError.ERROR.
     */
    public int getBluetoothState() {
        try {
            return mService.getBluetoothState();
        } catch (RemoteException e) {Log.e(TAG, "", e);}
        return BluetoothError.ERROR;
    }

    /**
     * Enable the Bluetooth device.
     * Turn on the underlying hardware.
     * This is an asynchronous call,
     * BluetoothIntent.BLUETOOTH_STATE_CHANGED_ACTION can be used to check if
     * and when the device is sucessfully enabled.
     * @return false if we cannot enable the Bluetooth device. True does not
     * imply the device was enabled, it only implies that so far there were no
     * problems.
     */
    public boolean enable() {
        try {
            return mService.enable();
        } catch (RemoteException e) {Log.e(TAG, "", e);}
        return false;
    }

    /**
     * Disable the Bluetooth device.
     * This turns off the underlying hardware.
     *
     * @return true if successful, false otherwise.
     */
    public boolean disable() {
        try {
            return mService.disable(true);
        } catch (RemoteException e) {Log.e(TAG, "", e);}
        return false;
    }

    public String getAddress() {
        try {
            return mService.getAddress();
        } catch (RemoteException e) {Log.e(TAG, "", e);}
        return null;
    }

    /**
     * Get the friendly Bluetooth name of this device.
     *
     * This name is visible to remote Bluetooth devices. Currently it is only
     * possible to retrieve the Bluetooth name when Bluetooth is enabled.
     *
     * @return the Bluetooth name, or null if there was a problem.
     */
    public String getName() {
        try {
            return mService.getName();
        } catch (RemoteException e) {Log.e(TAG, "", e);}
        return null;
    }

    /**
     * Set the friendly Bluetooth name of this device.
     *
     * This name is visible to remote Bluetooth devices. The Bluetooth Service
     * is responsible for persisting this name.
     *
     * @param name the name to set
     * @return     true, if the name was successfully set. False otherwise.
     */
    public boolean setName(String name) {
        try {
            return mService.setName(name);
        } catch (RemoteException e) {Log.e(TAG, "", e);}
        return false;
    }

    /**
     * Get the current scan mode.
     * Used to determine if the local device is connectable and/or discoverable
     * @return Scan mode, one of SCAN_MODE_* or an error code
     */
    public int getScanMode() {
        try {
            return mService.getScanMode();
        } catch (RemoteException e) {Log.e(TAG, "", e);}
        return BluetoothError.ERROR_IPC;
    }

    /**
     * Set the current scan mode.
     * Used to make the local device connectable and/or discoverable
     * @param scanMode One of SCAN_MODE_*
     */
    public void setScanMode(int scanMode) {
        try {
            mService.setScanMode(scanMode);
        } catch (RemoteException e) {Log.e(TAG, "", e);}
    }

    public int getDiscoverableTimeout() {
        try {
            return mService.getDiscoverableTimeout();
        } catch (RemoteException e) {Log.e(TAG, "", e);}
        return -1;
    }

    public void setDiscoverableTimeout(int timeout) {
        try {
            mService.setDiscoverableTimeout(timeout);
        } catch (RemoteException e) {Log.e(TAG, "", e);}
    }

    public boolean startDiscovery() {
        try {
            return mService.startDiscovery();
        } catch (RemoteException e) {Log.e(TAG, "", e);}
        return false;
    }

    public void cancelDiscovery() {
        try {
            mService.cancelDiscovery();
        } catch (RemoteException e) {Log.e(TAG, "", e);}
    }

    public boolean isDiscovering() {
        try {
            return mService.isDiscovering();
        } catch (RemoteException e) {Log.e(TAG, "", e);}
        return false;
    }

    /**
     * List remote devices that are bonded (paired) to the local adapter.
     *
     * Bonding (pairing) is the process by which the user enters a pin code for
     * the device, which generates a shared link key, allowing for
     * authentication and encryption of future connections. In Android we
     * require bonding before RFCOMM or SCO connections can be made to a remote
     * device.
     *
     * This function lists which remote devices we have a link key for. It does
     * not cause any RF transmission, and does not check if the remote device
     * still has it's link key with us. If the other side no longer has its
     * link key then the RFCOMM or SCO connection attempt will result in an
     * error.
     *
     * This function does not check if the remote device is in range.
     *
     * Remote devices that have an in-progress bonding attempt are not
     * returned.
     *
     * @return unmodifiable set of bonded devices, or null on error
     */
    public Set<BluetoothDevice> getBondedDevices() {
        try {
            return toDeviceSet(mService.listBonds());
        } catch (RemoteException e) {Log.e(TAG, "", e);}
        return null;
    }

    /**
     * Construct a listening, secure RFCOMM server socket.
     * The remote device connecting to this socket will be authenticated and
     * communication on this socket will be encrypted.
     * Call #accept to retrieve connections to this socket.
     * @return An RFCOMM BluetoothServerSocket
     * @throws IOException On error, for example Bluetooth not available, or
     *                     insufficient permissions.
     */
    public BluetoothServerSocket listenUsingRfcommOn(int port) throws IOException {
        BluetoothServerSocket socket = new BluetoothServerSocket(
                BluetoothSocket.TYPE_RFCOMM, true, true, port);
        try {
            socket.mSocket.bindListenNative();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e2) { }
            throw e;
        }
        return socket;
    }

    /**
     * Construct an unencrypted, unauthenticated, RFCOMM server socket.
     * Call #accept to retrieve connections to this socket.
     * @return An RFCOMM BluetoothServerSocket
     * @throws IOException On error, for example Bluetooth not available, or
     *                     insufficient permissions.
     */
    public BluetoothServerSocket listenUsingInsecureRfcommOn(int port) throws IOException {
        BluetoothServerSocket socket = new BluetoothServerSocket(
                BluetoothSocket.TYPE_RFCOMM, false, false, port);
        try {
            socket.mSocket.bindListenNative();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e2) { }
            throw e;
        }
        return socket;
    }

    /**
     * Construct a SCO server socket.
     * Call #accept to retrieve connections to this socket.
     * @return A SCO BluetoothServerSocket
     * @throws IOException On error, for example Bluetooth not available, or
     *                     insufficient permissions.
     */
    public static BluetoothServerSocket listenUsingScoOn() throws IOException {
        BluetoothServerSocket socket = new BluetoothServerSocket(
                BluetoothSocket.TYPE_SCO, false, false, -1);
        try {
            socket.mSocket.bindListenNative();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e2) { }
            throw e;
        }
        return socket;
    }

    private Set<BluetoothDevice> toDeviceSet(String[] addresses) {
        Set<BluetoothDevice> devices = new HashSet<BluetoothDevice>(addresses.length);
        for (int i = 0; i < addresses.length; i++) {
            devices.add(getRemoteDevice(addresses[i]));
        }
        return Collections.unmodifiableSet(devices);
    }
}
