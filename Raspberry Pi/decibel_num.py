import pyaudio
import numpy as np
import tkinter as tk

# Parameters for the audio stream
CHUNK = 1024
FORMAT = pyaudio.paInt16
CHANNELS = 1
RATE = 44100

# Create an instance of PyAudio and open a stream
p = pyaudio.PyAudio()
stream = p.open(format=FORMAT,
                channels=CHANNELS,
                rate=RATE,
                input=True,
                frames_per_buffer=CHUNK)

# Create a GUI window using tkinter
root = tk.Tk()
root.title('Sound Level Meter')

# Create a label for the decibel level
label = tk.Label(root, text='0 dB', font=('Arial', 30))
label.pack()

# Define a function to update the label with the current decibel level
def update_label():
    # Read audio data from the stream
    data = stream.read(CHUNK, exception_on_overflow=False)

    # Convert the raw data to a numpy array
    audio = np.frombuffer(data, dtype=np.int16)

    # Calculate the root mean square (RMS) of the audio signal
    rms = np.sqrt(np.mean(np.square(audio)))

    # Convert the RMS to decibels
    db = 20 * np.log10(rms)

    # Update the label with the new decibel value
    label.configure(text=f'{db:.2f} dB')

    # Schedule the next update
    root.after(100, update_label)

# Start the GUI and schedule the first update
update_label()
root.mainloop()
