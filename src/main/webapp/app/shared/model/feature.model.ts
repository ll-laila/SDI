import { type IModule } from '@/shared/model/module.model';

export interface IFeature {
  id?: number;
  name?: string;
  code?: string | null;
  apiVersion?: string | null;
  description?: string | null;
  notes?: string | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
  module?: IModule | null;
}

export class Feature implements IFeature {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string | null,
    public apiVersion?: string | null,
    public description?: string | null,
    public notes?: string | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public module?: IModule | null,
  ) {}
}
