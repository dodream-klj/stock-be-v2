import FinanceDataReader as fdr
import os
import sys
import json

# 쉼표로 구분된 종목 코드를 입력받기
stock_codes_input = sys.argv[1]  # 예: "005930,000660,035420"
stock_codes = stock_codes_input.split(',')  # 쉼표로 분리하여 리스트로 변환

# KRX 종목 코드 불러오기
krx = fdr.StockListing('KRX')

# 종목 코드에 맞는 항목 필터링
filtered_stocks = krx[krx['Code'].isin(stock_codes)]

# 검색된 결과를 JSON 형식으로 변환
filtered_stocks_json = filtered_stocks.to_json(orient='records', force_ascii=False)

# JSON 배열 출력
parsed_json = json.loads(filtered_stocks_json)  # JSON 문자열을 파이썬 객체로 변환
if parsed_json:  # 데이터가 존재할 경우
    print(filtered_stocks_json)  # JSON 포맷 출력
else:
    print(json.dumps({"error": f"'{stock_codes}'에 대한 검색 결과가 없습니다."}, ensure_ascii=False, indent=4))