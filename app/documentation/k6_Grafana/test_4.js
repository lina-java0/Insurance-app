import http from 'k6/http';
import { check } from 'k6';

export const options = {
  stages: [
    { duration: '10s', target: 100 },   // разгон
    { duration: '10s', target: 300 },
    { duration: '10s', target: 600 },
    { duration: '10s', target: 1000 },  // пик
    { duration: '20s', target: 1000 },  // держим нагрузку
    { duration: '10s', target: 0 },     // спуск
  ],
  thresholds: {
    http_req_failed: ['rate<0.05'],      // <5% ошибок
    http_req_duration: ['p(95)<500'],    // 95% запросов < 500мс
  },
};

export default function () {
  const url = 'http://localhost:8080/insurance/travel/api/v2';
  const payload = JSON.stringify({
    agreementDateFrom: '2040-05-25',
    agreementDateTo: '2040-05-29',
    country: 'SPAIN',
    selected_risks: ['TRAVEL_MEDICAL'],
    people: [
      {
        personFirstName: 'Fedor',
        personLastName: 'Vencak',
        personCode: '123456-12345',
        personBirthDate: '1998-08-16',
        medicalRiskLimitLevel: 'LEVEL_10000',
      },
      {
        personFirstName: 'Polina',
        personLastName: 'Konoshenko',
        personCode: '123456-12345',
        personBirthDate: '2000-11-24',
        medicalRiskLimitLevel: 'LEVEL_10000',
      },
    ],
  });

  const params = { headers: { 'Content-Type': 'application/json' } };

  const res = http.post(url, payload, params);

  check(res, {
    'status is 200': (r) => r.status === 200,
    'response has agreementPremium': (r) => r.json('agreementPremium') !== undefined,
  });
}