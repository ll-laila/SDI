import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import ActionRequestService from './action-request.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { ActionRequest } from '@/shared/model/action-request.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('ActionRequest Service', () => {
    let service: ActionRequestService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new ActionRequestService();
      currentDate = new Date();
      elemDefault = new ActionRequest(123, 'AAAAAAA', 'AAAAAAA', 'PENDING', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = {
          createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a ActionRequest', async () => {
        const returnedFromService = {
          id: 123,
          createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };
        const expected = { createdAt: currentDate, updatedAt: currentDate, ...returnedFromService };

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a ActionRequest', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a ActionRequest', async () => {
        const returnedFromService = {
          entityType: 'BBBBBB',
          newData: 'BBBBBB',
          status: 'BBBBBB',
          createdBy: 'BBBBBB',
          approvedBy: 'BBBBBB',
          createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };

        const expected = { createdAt: currentDate, updatedAt: currentDate, ...returnedFromService };
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a ActionRequest', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a ActionRequest', async () => {
        const patchObject = {
          entityType: 'BBBBBB',
          status: 'BBBBBB',
          createdBy: 'BBBBBB',
          createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...new ActionRequest(),
        };
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = { createdAt: currentDate, updatedAt: currentDate, ...returnedFromService };
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a ActionRequest', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of ActionRequest', async () => {
        const returnedFromService = {
          entityType: 'BBBBBB',
          newData: 'BBBBBB',
          status: 'BBBBBB',
          createdBy: 'BBBBBB',
          approvedBy: 'BBBBBB',
          createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };
        const expected = { createdAt: currentDate, updatedAt: currentDate, ...returnedFromService };
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of ActionRequest', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a ActionRequest', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a ActionRequest', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
