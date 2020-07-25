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

package io.github.randomfilemaker.ui.make;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import io.github.randomfilemaker.MainActivity;
import io.github.randomfilemaker.R;

public class MakeFragment extends Fragment {

	private Button mButtonMake;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.fragment_make, container, false);
		mButtonMake = root.findViewById(R.id.buttonMake);
		mButtonMake.setOnClickListener(v -> ((MainActivity) getActivity()).startMakeProcess());
		((MainActivity) getActivity()).mEditTextSize = root.findViewById(R.id.editTextSize);
		((MainActivity) getActivity()).mRadioButtonBytes = root.findViewById(R.id.radioButtonBytes);
		((MainActivity) getActivity()).mRadioButtonKB = root.findViewById(R.id.radioButtonKB);
		((MainActivity) getActivity()).mRadioButtonMB = root.findViewById(R.id.radioButtonMB);
		((MainActivity) getActivity()).mRadioButtonGB = root.findViewById(R.id.radioButtonGB);
		return root;
	}

}