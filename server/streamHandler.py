from flask import Flask, Response
import subprocess
import os

app = Flask(__name__)

# Alamat IP ESP32CAM dan port streaming MJPEG
ESP32CAM_IP = '192.168.0.7'
STREAM_PORT = '80'

# Route untuk menyediakan playlist HLS
@app.route('/stream.m3u8')
def stream_playlist():
    return Response(get_hls_playlist(), mimetype='application/vnd.apple.mpegurl')

# Route untuk menyediakan segmen video HLS
@app.route('/stream/<int:segment>.ts')
def stream_segment(segment):
    return Response(get_hls_segment(segment), mimetype='video/MP2T')

# Fungsi untuk menghasilkan playlist HLS
def get_hls_playlist():
    return render_template('stream.m3u8')

# Fungsi untuk menghasilkan segmen video HLS
def get_hls_segment(segment):
    segment_path = f'segments/{segment}.ts'
    with open(segment_path, 'rb') as f:
        return f.read()

# Jalankan proses FFMPEG untuk melakukan konversi MJPEG menjadi HLS
def start_ffmpeg():
    os.makedirs('segments', exist_ok=True)
    subprocess.Popen([
        'ffmpeg',
        '-i', f'http://{ESP32CAM_IP}:{STREAM_PORT}/stream',
        '-c:v', 'libx264',
        '-crf', '20',
        '-preset', 'ultrafast',
        '-g', '60',
        '-hls_time', '2',
        '-hls_list_size', '10',
        '-hls_flags', 'delete_segments',
        '-start_number', '1',
        '-f', 'hls',
        'segments/stream.m3u8'
    ])

# Mulai proses FFMPEG saat server Flask dimulai
if __name__ == '__main__':
    start_ffmpeg()
    app.run(debug=True, host='0.0.0.0', port=3000)
