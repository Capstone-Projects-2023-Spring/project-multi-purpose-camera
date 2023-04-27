sleep(15)

sudo bluetoothctl <<EOF
power on
discoverable on
pairable on
EOF

bt-agent -c NoInputNoOutput -p pin -d