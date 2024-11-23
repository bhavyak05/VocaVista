package com.example.vocavista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial

class NotificationSettingsFragment : Fragment(R.layout.fragment_notifications) {

    private lateinit var enableNotificationsSwitch: SwitchMaterial
    private lateinit var soundSwitch: SwitchMaterial
    private lateinit var vibrationSwitch: SwitchMaterial

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        // Initialize switches
        enableNotificationsSwitch = view.findViewById(R.id.switch_enable_notifications)
        soundSwitch = view.findViewById(R.id.switch_sound)
        vibrationSwitch = view.findViewById(R.id.switch_vibration)

        // Set listeners
        enableNotificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), "Notifications: ${if (isChecked) "Enabled" else "Disabled"}", Toast.LENGTH_SHORT).show()
        }

        soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), "Sound: ${if (isChecked) "On" else "Off"}", Toast.LENGTH_SHORT).show()
        }

        vibrationSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), "Vibration: ${if (isChecked) "On" else "Off"}", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
