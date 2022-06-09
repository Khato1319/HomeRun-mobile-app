package com.hci.homerunapp.ui.recents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hci.homerunapp.MainActivity;
import com.hci.homerunapp.databinding.FragmentRecentsBinding;

public class RecentsFragment extends Fragment {

    private FragmentRecentsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecentsViewModel recentsViewModel =
                new ViewModelProvider(this).get(RecentsViewModel.class);

        binding = FragmentRecentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        MainActivity mainActivity = (MainActivity) getActivity();
//        assert mainActivity != null;
//        mainActivity.showBottomNav();

        final TextView textView = binding.textDashboard;
        recentsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}