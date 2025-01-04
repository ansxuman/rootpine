package com.ansxuman.rootpine.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ansxuman.rootpine.data.model.RootCheckResult;
import com.ansxuman.rootpine.data.repository.RootCheckRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {
    private final RootCheckRepository repository;
    private final MutableLiveData<List<RootCheckResult>> checkResults = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new RootCheckRepository(application);
    }

    public void performChecks() {
        checkResults.setValue(null);
        isLoading.setValue(true);
        executor.execute(() -> {
            List<RootCheckResult> results = repository.performAllChecks();
            checkResults.postValue(results);
            isLoading.postValue(false);
        });
    }

    public LiveData<List<RootCheckResult>> getCheckResults() {
        return checkResults;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}