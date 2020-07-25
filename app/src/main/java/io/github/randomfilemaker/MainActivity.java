//    The GNU General Public License does not permit incorporating this program
//    into proprietary programs.
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <https://www.gnu.org/licenses/>.

package io.github.randomfilemaker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;

import id.ionbit.ionalert.IonAlert;
import io.github.randomfilemaker.tools.MakeFile;
import io.github.randomfilemaker.ui.Alert;

public class MainActivity extends CyaneaAppCompatActivity {

	public EditText mEditTextSize;
	public RadioButton mRadioButtonBytes;
	public RadioButton mRadioButtonKB;
	public RadioButton mRadioButtonMB;
	public RadioButton mRadioButtonGB;
	IonAlert mMaking;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				startMakeFile(uri);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BottomNavigationView navView = findViewById(R.id.nav_view);
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_make,
				R.id.navigation_configuration, R.id.navigation_about).build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(navView, navController);
	}

	private void startMakeFile(Uri uri) {
		mMaking = new Alert(this).making();
		mMaking.show();
		int size = Integer.parseInt(mEditTextSize.getText().toString());
		if (mRadioButtonKB.isChecked())
			size *= Math.pow(1024, 1);
		else if (mRadioButtonMB.isChecked())
			size *= Math.pow(1024, 2);
		else if (mRadioButtonGB.isChecked())
			size *= Math.pow(1024, 3);
		int finalSize = size;
		new Thread(() -> {
			try {
				OutputStream os = getContentResolver().openOutputStream(uri);
				new MakeFile().make(finalSize, os);
				mEditTextSize.post(() -> whenDone());
			} catch (IOException f) {
				mEditTextSize.post(() -> whenError(f.toString()));
			}

		}).start();
	}

	public void startMakeProcess() {
		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, 1);
	}

	private void whenDone() {
		mMaking.dismissWithAnimation();
		new Alert(this).done();
	}

	private void whenError(String error) {
		mMaking.dismissWithAnimation();
		new Alert(this).errorMakeFile(error);
	}
}