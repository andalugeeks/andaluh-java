#!/usr/bin/env python3
import argparse
import csv
import sys
from pathlib import Path


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Generate a lemario CSV with Python andaluh-py outputs."
    )
    parser.add_argument(
        "--input",
        default="../andalugeeks-andaluh-py-18621fe/tests/lemario_cas_and.csv",
        help="Input CSV with 'cas' column.",
    )
    parser.add_argument(
        "--output",
        default="out/lemario_py.csv",
        help="Output CSV path to write (will contain 'cas','and').",
    )
    parser.add_argument(
        "--andaluh-py",
        dest="andaluh_py",
        default="../andalugeeks-andaluh-py-18621fe",
        help="Path to andaluh-py repo (for import).",
    )
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    andaluh_path = Path(args.andaluh_py).resolve()
    if not andaluh_path.exists():
        print(f"andaluh-py path not found: {andaluh_path}")
        return 1

    sys.path.insert(0, str(andaluh_path))
    try:
        import andaluh  # type: ignore
    except Exception as exc:
        print(f"Failed to import andaluh from {andaluh_path}: {exc}")
        return 1

    input_path = Path(args.input).resolve()
    if not input_path.exists():
        print(f"Input CSV not found: {input_path}")
        return 1

    output_path = Path(args.output).resolve()
    output_path.parent.mkdir(parents=True, exist_ok=True)

    total = 0
    with input_path.open(newline="", encoding="utf-8") as fh_in, output_path.open(
        "w", newline="", encoding="utf-8"
    ) as fh_out:
        reader = csv.DictReader(fh_in, delimiter=",")
        writer = csv.DictWriter(fh_out, fieldnames=["cas", "and"], quoting=csv.QUOTE_MINIMAL)
        writer.writeheader()
        for row in reader:
            cas = row.get("cas", "")
            andal = andaluh.epa(cas)
            writer.writerow({"cas": cas, "and": andal})
            total += 1

    print(f"Wrote {total} rows to {output_path}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
