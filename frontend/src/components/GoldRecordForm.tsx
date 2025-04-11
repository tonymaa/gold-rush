import React, { useState } from 'react';
import { Form, Input, DatePicker, Switch, Upload, Button, message } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import type { UploadFile } from 'antd/es/upload/interface';
import type { GoldRecord } from '../types/GoldRecord';
import moment from 'moment';

interface Props {
    initialValues?: GoldRecord;
    onSubmit: (values: GoldRecord, photo?: File) => Promise<void>;
}

const GoldRecordForm: React.FC<Props> = ({ initialValues, onSubmit }) => {
    const [form] = Form.useForm();
    const [photo, setPhoto] = useState<File>();
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (values: any) => {
        try {
            setLoading(true);
            const record: GoldRecord = {
                ...values,
                purchaseDate: values.purchaseDate.format('YYYY-MM-DD'),
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
        <Form
            form={form}
            layout="vertical"
            onFinish={handleSubmit}
            initialValues={{
                ...initialValues,
                purchaseDate: initialValues?.purchaseDate ? moment(initialValues.purchaseDate) : moment(),
                isSummary: initialValues?.isSummary ?? false,
            }}
        >
            <Form.Item
                label="重量（克）"
                name="weight"
                rules={[{ required: true, message: '请输入重量' }]}
            >
                <Input type="number" step="0.01" min="0" />
            </Form.Item>

            <Form.Item
                label="成交总价"
                name="totalPrice"
                rules={[{ required: true, message: '请输入成交总价' }]}
            >
                <Input type="number" step="0.01" min="0" />
            </Form.Item>

            <Form.Item label="购买渠道" name="purchaseChannel">
                <Input />
            </Form.Item>

            <Form.Item label="备注" name="remarks">
                <Input.TextArea />
            </Form.Item>

            <Form.Item
                label="购买日期"
                name="purchaseDate"
                rules={[{ required: true, message: '请选择购买日期' }]}
            >
                <DatePicker style={{ width: '100%' }} />
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
                    <Button icon={<UploadOutlined />}>上传照片</Button>
                </Upload>
            </Form.Item>

            <Form.Item
                label="汇总模式"
                name="isSummary"
                valuePropName="checked"
            >
                <Switch />
            </Form.Item>

            <Form.Item>
                <Button type="primary" htmlType="submit" loading={loading} block>
                    保存
                </Button>
            </Form.Item>
        </Form>
    );
};

export default GoldRecordForm; 