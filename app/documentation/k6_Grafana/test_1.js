import http from 'k6/http';
import { check, sleep } from 'k6';

// Настройки нагрузки
export const options = {
  vus: 10,            // количество виртуальных пользователей
  duration: '20s',    // длительность теста
};

// URL твоего запущенного приложения
const BASE_URL = 'http://localhost:8080/insurance/travel/api/v2';

// Пример тела запроса
const requestBody = JSON.stringify({
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

// Заголовки
const headers = {
  'Content-Type': 'application/json',
};

export default function () {
  const res = http.post(BASE_URL, requestBody, { headers });

  // Проверяем, что сервер ответил успешно
  check(res, {
    'status is 200': (r) => r.status === 200,
  });

  sleep(1); // небольшая пауза между запросами
}