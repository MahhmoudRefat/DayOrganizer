package com.example.dayorganizer.Ui.home.settings


import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import com.example.dayorganizer.R
import com.example.dayorganizer.Ui.model.Constants
import com.example.dayorganizer.databinding.FragmentSettingsBinding
import java.util.Locale


class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    private lateinit var languageArrayAdapter: ArrayAdapter<String>

    override fun onResume() {
        super.onResume()
        // language
        val languages = resources.getStringArray(R.array.language_array)
        languageArrayAdapter =
            ArrayAdapter(requireContext(), R.layout.drop_down_item, languages)
        binding.autoCompleteTVLanguages.setAdapter(languageArrayAdapter)

        // mode
        val modes = resources.getStringArray(R.array.mode_array)
        val modeArrayAdapter =
            ArrayAdapter(requireContext(), R.layout.drop_down_item, modes)
        binding.autoCompleteTVModes.setAdapter(modeArrayAdapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeLanguage()
        changeNightMode()
    }

    private fun changeLanguage() {
        binding.autoCompleteTVLanguages.setOnItemClickListener{
            parent,_,position,_ ->
            val getSelected = parent.getItemAtPosition(position)
            if (getSelected == "العربيه"){
                setLocal("ar")
                Constants.isArabic = true
            }
        }
    }
    private fun setLocal(s: String) {
        val local = Locale(s)
        Locale.setDefault(local)
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(local)

        requireContext().resources.updateConfiguration(
            configuration,
            requireContext().resources.displayMetrics,
        )
        requireActivity().recreate()
    }
    private fun changeNightMode() {
        binding.autoCompleteTVModes.setOnItemClickListener { parent, _, position, _ ->
            val currentMode = parent.getItemAtPosition(position).toString()
            if ("Light" == currentMode || "نهاري" == currentMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Constants.isDark = false
            } else if ("Dark" == currentMode || "ليلي" == currentMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Constants.isDark = true
            }
        }
    }

}
