import FinanceDataReader as fdr
import os
import sys
import json

stock_code = sys.argv[1]

# KRX 종목 코드 불러오기
krx = fdr.StockListing('KRX')
# req = dict()

stock_info = krx[krx['Code'] == stock_code]
stock_info_json = stock_info.to_json(orient='records', force_ascii=False)

# JSON 배열에서 첫 번째 요소 출력
parsed_json = json.loads(stock_info_json)  # JSON 문자열을 파이썬 객체로 변환
if parsed_json:  # 데이터가 존재할 경우
    print(json.dumps(parsed_json[0], ensure_ascii=False, indent=4))  # 첫 번째 항목 출력
else:
    print(json.dumps({"error": "해당 종목 정보를 찾을 수 없습니다."}, ensure_ascii=False, indent=4))