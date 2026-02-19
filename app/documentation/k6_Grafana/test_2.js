import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '10s', target: 10 },  // 10 пользователей за 10 секунд
    { duration: '20s', target: 50 },  // увеличиваем до 50
    { duration: '30s', target: 100 }, // пик — 100 одновременных
    { duration: '20s', target: 0 },   // спад нагрузки
  ],
  thresholds: {
    http_req_duration: ['p(95)<300'], // 95% запросов должны быть быстрее 300мс
    http_req_failed: ['rate<0.05'],   // не более 5% ошибок
  },
};

const url = 'http://localhost:8080/insurance/travel/api/v2'; 

const payload = JSON.stringify({
  agreementDateFrom: "2040-05-25",
  agreementDateTo: "2040-05-29",
  country: "SPAIN",
  selected_risks: ["TRAVEL_MEDICAL"],
  people: [
    {
      personFirstName: "Fedor",
      personLastName: "Vencak",
      personCode: "123456-12345",
      personBirthDate: "1998-08-16",
      medicalRiskLimitLevel: "LEVEL_10000"
    },
    {
      personFirstName: "Polina",
      personLastName: "Konoshenko",
      personCode: "123456-12345",
      personBirthDate: "2000-11-24",
      medicalRiskLimitLevel: "LEVEL_10000"
    }
  ]
});

const params = {
  headers: {
    'Content-Type': 'application/json',
  },
};

export default function () {
  const res = http.post(url, payload, params);

  check(res, {
    'status is 200': (r) => r.status === 200,
    'response has agreementPremium': (r) =>
      r.json('agreementPremium') !== undefined,
  });

  sleep(1);
}