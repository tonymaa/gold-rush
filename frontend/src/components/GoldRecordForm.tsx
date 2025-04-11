import React, { useState } from 'react';
import { Form, Input, DatePicker, Switch, Upload, Button, message } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import type { UploadFile } from 'antd/es/upload/interface';
import type { GoldRecord } from '../types/GoldRecord';
import moment from 'moment';
import styled from 'styled-components';

interface Props {
    initialValues?: GoldRecord;
    onSubmit: (values: GoldRecord, photo?: File) => Promise<void>;
}

const StyledForm = styled(Form)`
    .ant-form-item-label > label {
        color: white;
    }
    .ant-input,
    .ant-input-number,
    .ant-picker {
        background: #3a3a3a;
        border-color: #4a4a4a;
        color: white;
    }
    .ant-input:hover,
    .ant-input-number:hover,
    .ant-picker:hover {
        border-color: #ffd700;
    }
    .ant-input:focus,
    .ant-input-number-focused,
    .ant-picker-focused {
        border-color: #ffd700;
        box-shadow: 0 0 0 2px rgba(255, 215, 0, 0.2);
    }
    .ant-switch-checked {
        background: #ffd700;
    }
    .ant-btn-primary {
        background: #ffd700;
        border-color: #ffd700;
        color: black;
        &:hover {
            background: #ffed4a;
            border-color: #ffed4a;
            color: black;
        }
    }
`;

const GoldRecordForm: React.FC<Props> = ({ initialValues, onSubmit }) => {
    const [form] = Form.useForm();
    const [photo, setPhoto] = useState<File>();
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (values: any) => {
        try {
            setLoading(true);
            const record: GoldRecord = {
                ...values,
                purchaseDate: values.purchaseDate,
            };
            await onSubmit(record, photo);
            message.success('保存成功');
            form.resetFields();
            setPhoto(undefined);
        } catch (error) {
            message.error('保存失败');
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    const normFile = (e: any) => {
        if (Array.isArray(e)) {
            return e;
        }
        return e?.fileList;
    };

    const beforeUpload = (file: File) => {
        const isImage = file.type.startsWith('image/');
        if (!isImage) {
            message.error('只能上传图片文件！');
            return false;
        }
        setPhoto(file);
        return false;
    };

    return (
        <StyledForm
            form={form}
            layout="vertical"
            onFinish={handleSubmit}
            initialValues={{
                ...initialValues,
                purchaseDate: initialValues?.purchaseDate ? moment(initialValues.purchaseDate) : moment(),
                isSummary: initialValues?.isSummary ?? false,
            }}
            style={{ background: '#2a2a2a', padding: '20px', borderRadius: '8px' }}
        >
            <Form.Item
                label="重量（克）"
                name="weight"
                rules={[{ required: true, message: '请输入重量' }]}
            >
                <Input type="number" step="0.01" min="0" placeholder="请输入重量" />
            </Form.Item>

            <Form.Item
                label="成交总价"
                name="totalPrice"
                rules={[{ required: true, message: '请输入成交总价' }]}
            >
                <Input type="number" step="0.01" min="0" placeholder="请输入成交总价" />
            </Form.Item>

            <Form.Item label="购买渠道" name="purchaseChannel">
                <Input placeholder="请输入购买渠道" />
            </Form.Item>

            <Form.Item label="备注" name="remarks">
                <Input.TextArea placeholder="请输入备注" />
            </Form.Item>

            <Form.Item
                label="购买日期"
                name="purchaseDate"
            >
                <DatePicker showTime style={{ width: '100%' }} />
            </Form.Item>

            <Form.Item
                label="照片"
                name="photo"
                valuePropName="fileList"
                getValueFromEvent={normFile}
            >
                <Upload
                    beforeUpload={beforeUpload}
                    maxCount={1}
                    listType="picture"
                >
                    <Button icon={<UploadOutlined />} style={{ background: '#3a3a3a', color: 'white', borderColor: '#4a4a4a' }}>
                        上传照片
                    </Button>
                </Upload>
            </Form.Item>

{/*            <Form.Item
                label="汇总模式"
                name="isSummary"
                valuePropName="checked"
            >
                <Switch />
            </Form.Item>*/}

            <Form.Item>
                <Button type="primary" htmlType="submit" loading={loading} block>
                    保存
                </Button>
            </Form.Item>
        </StyledForm>
    );
};

export default GoldRecordForm; 