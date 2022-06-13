package com.hci.homerunapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hci.homerunapp.R;
import com.hci.homerunapp.databinding.FragmentHomeBinding;
import com.hci.homerunapp.ui.ButtonListenerMaker;
import com.hci.homerunapp.ui.Data;


public abstract class SimpleButtonFragment extends Fragment implements ButtonListenerMaker {

    private FragmentHomeBinding binding;
    SimpleButtonAdapter adapter;
    SimpleButtonViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                getViewModel();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        adapter = getAdapter();

        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        binding.homeRecyclerView.setAdapter(adapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected abstract SimpleButtonViewModel getViewModel();

    public abstract View.OnClickListener getButtonClickListener(Data roomData);

    private SimpleButtonAdapter getAdapter(int buttonId, int layoutId) {
        return new SimpleButtonAdapter(homeViewModel.getElements(), this, R.id.simple_button, R.layout.simple_button_layout);
    }

    protected SimpleButtonAdapter getAdapter() {
        return getAdapter(R.id.simple_button, R.layout.simple_button_layout);
    }

    @Override
    public NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }
}