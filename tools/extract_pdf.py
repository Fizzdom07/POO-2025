import sys
from pathlib import Path
import subprocess

pdf_path = Path(r"C:\Users\Daniel Moreira\Desktop\Escola naval\5º ano\POO\Lab_04_POO.pdf")
out_path = pdf_path.with_suffix('.txt')

# Ensure pypdf is installed
try:
    from pypdf import PdfReader
except Exception:
    print('pypdf not found, installing...')
    subprocess.check_call([sys.executable, '-m', 'pip', 'install', 'pypdf'])
    from pypdf import PdfReader

reader = PdfReader(str(pdf_path))
text_parts = []
for i, page in enumerate(reader.pages):
    try:
        text = page.extract_text()
    except Exception:
        text = None
    if text:
        text_parts.append(f"--- PAGE {i+1} ---\n")
        text_parts.append(text)

if not text_parts:
    print('No text extracted (PDF may be scanned images).')
    # still create an empty file to indicate failure
    out_path.write_text('')
    sys.exit(1)

out_path.write_text('\n\n'.join(text_parts), encoding='utf-8')
print(f'Extracted text to: {out_path}')
