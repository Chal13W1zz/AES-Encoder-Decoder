package com.freetechmods.aes;


import android.app.*;
import android.os.*;
import android.content.*;
import android.content.pm.*;
import android.util.*;
import android.view.*;
import javax.crypto.*;
import java.io.*;
import javax.crypto.spec.*;
import java.util.zip.*;
import android.widget.*;

public class MainActivity extends Activity 
{
	String decrypt;
	String encrypt;
	TextView main;
	EditText dara,ki;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		main = (TextView) findViewById(R.id.mainTextView);
		dara = (EditText) findViewById(R.id.mainEditText1);
		ki = (EditText) findViewById(R.id.edkey);
		

		/*try{

		

		 KeyGenerator keyGenerator;
		 SecretKey secretKey;
		 keyGenerator = KeyGenerator.getInstance("AES");
		 keyGenerator.init(256);
		 secretKey = keyGenerator.generateKey();

		 ki.setText(secretKey.toString());
	}catch (Exception e){}
*/

	}


	public void dc(View v){

		String raw = dara.getText().toString();
		String key = ki.getText().toString();
		try{
			decrypt = new SimpleCrypt(key).decrypt(raw);
			main.setText(decrypt);			
			File root = new File(Environment.getExternalStorageDirectory(), "Notes");
			if (!root.exists()) {
				root.mkdirs();
			}
			File filepath = new File(root, "decoded.txt");
			FileWriter writer = new FileWriter(filepath);
			writer.append(decrypt);
			writer.flush();
			writer.close();
			Toast.makeText(this,"File decryption successful, saved at Notes/decoded.txt",Toast.LENGTH_LONG).show();

		}catch (Exception e){


		}

	}
	public void cr(View v){
		String raw = dara.getText().toString();
		String key = ki.getText().toString();

		try{

			encrypt = new SimpleCrypt(key).encrypt(raw);
			main.setText(encrypt);
			File toor = new File(Environment.getExternalStorageDirectory(), "Notes");
			if(!toor.exists()){
				toor.mkdirs();
			}
			File path = new File(toor, "encoded.txt");
			FileWriter write = new FileWriter(path);
			write.append(encrypt);
			write.flush();
			write.close();
			Toast.makeText(this,"File encryption successful, saved at Notes/encoded.txt",Toast.LENGTH_LONG).show();


		}catch(Exception e){

		}
	}

	public class SimpleCrypt {
		private String mKey;

		public SimpleCrypt(String key) {
			this.mKey = key;
		}

		public String encrypt(String plainText) {
			try {
				return Base64.encodeToString(getCipher(1).doFinal(plainText.getBytes()), 0);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public byte[] encrypt(byte[] plainBytes) {
			try {
				return getCipher(1).doFinal(plainBytes);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public String decrypt(String encryptedText) throws IOException {
			try {
				return new String(getCipher(2).doFinal(Base64.decode(encryptedText, 0)));
			} catch (Exception e) {
				throw new IOException(e.toString());
			}
		}

		public byte[] decrypt(byte[] encryptedBytes) throws IOException {
			try {
				return getCipher(2).doFinal(encryptedBytes);
			} catch (Exception e) {
				throw new IOException(e.toString());
			}
		}

		private Cipher getCipher(int cipherMode) throws Exception{
			String encryptionAlgorithm = "AES";
			SecretKeySpec keySpecification = new SecretKeySpec(this.mKey.getBytes("UTF-8"),encryptionAlgorithm);
			Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
			cipher.init(cipherMode, keySpecification);
			return cipher;
		}
	}




}
	



