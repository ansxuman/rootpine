package com.ansxuman.rootpine;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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

        binding.githubButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, 
                Uri.parse("https://github.com/ansxuman"));
            startActivity(intent);
        });

        binding.infoButton.setOnClickListener(v -> showAboutDialog());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.checkRootButton.setEnabled(!isLoading);
            if (isLoading) {
                binding.checkRootButton.setText("      Checking...");
                binding.checkRootButton.setIcon(null);
                binding.progressIndicator.setVisibility(View.VISIBLE);
                binding.recyclerResults.setVisibility(View.GONE);
            } else {
                binding.checkRootButton.setText("Check Root Status");
                binding.progressIndicator.setVisibility(View.GONE);
            }
        });

        viewModel.getCheckResults().observe(this, results -> {
            if (results != null) {
                binding.recyclerResults.setVisibility(View.VISIBLE);
                adapter.setResults(results);
            }
        });
    }

    private void showAboutDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_about, null);
        
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_App_MaterialAlertDialog);
        AlertDialog dialog = builder
            .setView(dialogView)
            .create();

        TextView versionText = dialogView.findViewById(R.id.version_text);
        try {
            String versionName = getPackageManager()
                .getPackageInfo(getPackageName(), 0).versionName;
            versionText.setText("Version " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            versionText.setVisibility(View.GONE);
        }

        TextView githubLink = dialogView.findViewById(R.id.github_link);
        githubLink.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, 
                Uri.parse("https://github.com/ansxuman"));
            startActivity(intent);
        });

        MaterialButton rateButton = dialogView.findViewById(R.id.rate_button);
        rateButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, 
                Uri.parse(getString(R.string.play_store_link)));
            startActivity(intent);
            dialog.dismiss();
        });

        dialog.show();

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }
}