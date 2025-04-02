import { type ActionRequestStatus } from '@/shared/model/enumerations/action-request-status.model';
export interface IActionRequest {
  id?: number;
  entityType?: string;
  newData?: string;
  status?: keyof typeof ActionRequestStatus;
  createdBy?: string;
  approvedBy?: string | null;
  createdAt?: Date | null;
  updatedAt?: Date | null;
}

export class ActionRequest implements IActionRequest {
  constructor(
    public id?: number,
    public entityType?: string,
    public newData?: string,
    public status?: keyof typeof ActionRequestStatus,
    public createdBy?: string,
    public approvedBy?: string | null,
    public createdAt?: Date | null,
    public updatedAt?: Date | null,
  ) {}
}
