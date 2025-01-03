package com.ansxuman.rootpine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ansxuman.rootpine.databinding.ActivityMainBinding;
import com.ansxuman.rootpine.ui.adapter.CheckResultAdapter;
import com.ansxuman.rootpine.ui.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private CheckResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupViews();
        observeViewModel();
    }

    private void setupViews() {
        adapter = new CheckResultAdapter();
        binding.recyclerResults.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerResults.setAdapter(adapter);

        binding.checkRootButton.setOnClickListener(v -> viewModel.performChecks());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.githubButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, 
                Uri.parse("https://github.com/ansxuman"));
            startActivity(intent);
        });
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.scanningAnimation.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            if (isLoading) {
                binding.scanningAnimation.playAnimation();
            } else {
                binding.scanningAnimation.pauseAnimation();
            }
            binding.checkRootButton.setEnabled(!isLoading);
        });

        viewModel.getCheckResults().observe(this, results -> {
            binding.recyclerResults.setVisibility(View.VISIBLE);
            adapter.setResults(results);
        });
    }
}